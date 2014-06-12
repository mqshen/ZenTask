package util

import java.io.IOException

/**
 * Created by GoldRatio on 4/18/14.
 */
class TrackerClient {
  var errno:Byte = 0
  val tracker_group = ClientGlobal.g_tracker_group

  def getUpdateStorage(trackerServer: TrackerServer, groupName: String , filename: String ) = {
    val servers = this.getStorages(trackerServer,
      ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE,
      groupName, filename)
    if (servers == null) {
      null
    }
    else {
      new StorageServer(servers(0).ip_addr, servers(0).port, 0)
    }
  }

  def getConnection() = {
    this.tracker_group.getConnection()
  }


  def getStoreStorage(trackerServer: TrackerServer , groupName: String ):StorageServer = {
    val (server, bNewConnection) = if (trackerServer == null) {
      (getConnection(), true)
    }
    else {
      (trackerServer, false)
    }

    val trackerSocket = server.socket
    val out = trackerSocket.getOutputStream()

    try {
      val (cmd, out_len) = if (groupName == null || groupName.length() == 0) {
          (ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE ,0)
        }
        else {
          (ProtoCommon.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE,
          ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
        }

      val header = ProtoCommon.packHeader(cmd, out_len, 0)
      out.write(header)

      if (groupName != null && groupName.length() > 0) {

        val bs = groupName.getBytes(ClientGlobal.g_charset)
        val bGroupName = Array.fill[Byte](ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)(0)

        val group_len =
        if (bs.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN) {
          bs.length
        }
        else {
          ProtoCommon.FDFS_GROUP_NAME_MAX_LEN
        }
        System.arraycopy(bs, 0, bGroupName, 0, group_len)
        out.write(bGroupName)
      }

      val pkgInfo = ProtoCommon.recvPackage(trackerSocket.getInputStream(),
        ProtoCommon.TRACKER_PROTO_CMD_RESP,
        ProtoCommon.TRACKER_QUERY_STORAGE_STORE_BODY_LEN)
      this.errno = pkgInfo.errno
      if (pkgInfo.errno == 0) {

        val ip_addr = new String(pkgInfo.body,
          ProtoCommon.FDFS_GROUP_NAME_MAX_LEN,
          ProtoCommon.FDFS_IPADDR_SIZE - 1).trim()

        val port = ProtoCommon.buff2long(pkgInfo.body,
          ProtoCommon.FDFS_GROUP_NAME_MAX_LEN + ProtoCommon.FDFS_IPADDR_SIZE - 1)
        val store_path = pkgInfo.body(ProtoCommon.TRACKER_QUERY_STORAGE_STORE_BODY_LEN - 1)

        new StorageServer(ip_addr, port, store_path)
      }
      else {
        null
      }
    }
    catch {
      case ex: IOException =>
        if (!bNewConnection) {
          try {
            trackerServer.close()
          }
          catch {
            case ex1: IOException =>
            ex1.printStackTrace()
          }
        }
        throw ex
    }
    finally {
      if (bNewConnection) {
        try {
          trackerServer.close()
        }
        catch {
          case ex1: IOException =>
            ex1.printStackTrace()
        }
      }
    }
  }


  def getStorages(trackerServer: TrackerServer , cmd: Byte, groupName: String , filename: String ):Array[ServerInfo] = {

    val (server, bNewConnection ) = if (trackerServer == null) {
      (getConnection(), true)
    }
    else {
      (trackerServer, false)
    }
    val trackerSocket = server.socket
    val out = trackerSocket.getOutputStream()

    try {
      val bs = groupName.getBytes(ClientGlobal.g_charset)
      val bGroupName = Array.fill[Byte](ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)(0)
      val bFileName = filename.getBytes(ClientGlobal.g_charset);

      val len =
      if (bs.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN) {
        bs.length
      }
      else {
        ProtoCommon.FDFS_GROUP_NAME_MAX_LEN
      }
      System.arraycopy(bs, 0, bGroupName, 0, len)

      val header = ProtoCommon.packHeader(cmd, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN + bFileName.length, 0)
      val wholePkg = new Array[Byte](header.length + bGroupName.length + bFileName.length)
      System.arraycopy(header, 0, wholePkg, 0, header.length);
      System.arraycopy(bGroupName, 0, wholePkg, header.length, bGroupName.length)
      System.arraycopy(bFileName, 0, wholePkg, header.length + bGroupName.length, bFileName.length)
      out.write(wholePkg);

      val pkgInfo = ProtoCommon.recvPackage(trackerSocket.getInputStream(),
        ProtoCommon.TRACKER_PROTO_CMD_RESP, -1)
      this.errno = pkgInfo.errno
      if (pkgInfo.errno != 0) {
        return null;
      }

      if (pkgInfo.body.length < ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN) {
        throw new IOException("Invalid body length: " + pkgInfo.body.length)
      }

      if ((pkgInfo.body.length - ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN) % (ProtoCommon.FDFS_IPADDR_SIZE - 1) != 0) {
        throw new IOException("Invalid body length: " + pkgInfo.body.length)
      }

      val server_count = 1 + (pkgInfo.body.length - ProtoCommon.TRACKER_QUERY_STORAGE_FETCH_BODY_LEN) / (ProtoCommon.FDFS_IPADDR_SIZE - 1)

      val ip_addr = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, ProtoCommon.FDFS_IPADDR_SIZE-1).trim()
      val offset = ProtoCommon.FDFS_GROUP_NAME_MAX_LEN + ProtoCommon.FDFS_IPADDR_SIZE - 1

      val port = ProtoCommon.buff2long(pkgInfo.body, offset)
      val currentOffset = offset + ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE

      val servers = new Array[ServerInfo](server_count)
      servers(0) = new ServerInfo(ip_addr, port)
      def getServer(index: Int, offset: Int) {
        servers(index) = new ServerInfo(new String(pkgInfo.body, offset, ProtoCommon.FDFS_IPADDR_SIZE-1).trim(), port)
        if(index < server_count)
        getServer(index + 1, offset + ProtoCommon.FDFS_IPADDR_SIZE - 1)
      }
      getServer(1, currentOffset)
      servers
    }
    catch  {
      case ex:IOException =>
        if (!bNewConnection) {
          try {
            trackerServer.close()
          }
          catch {
            case ex1: IOException =>
              ex1.printStackTrace()
          }
        }
        throw ex
      }
      finally {
        if (bNewConnection) {
          try {
            trackerServer.close()
          }
          catch {
            case ex1: IOException =>
            ex1.printStackTrace()
          }
        }
      }
    }



}
