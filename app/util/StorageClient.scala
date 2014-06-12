package util

import org.apache.http.NameValuePair
import java.io.{InputStream, IOException, FileInputStream, File}
import java.net.{InetSocketAddress, Socket}

/**
 * Created by GoldRatio on 4/18/14.
 */
object ClientGlobal {
  val g_charset = "UTF-8"


  val tracker_servers = new Array[InetSocketAddress](1)
  (0 until tracker_servers.length).foreach{ i =>
    tracker_servers(i) = new InetSocketAddress("101.251.195.189", 22122)
  }
  val g_tracker_group = new TrackerGroup(tracker_servers)
  val g_network_timeout = 5000
  val g_connect_timeout = 5000


  def getSocket(ip_addr: String , port: Int) = {
    val sock = new Socket()
    sock.setSoTimeout(ClientGlobal.g_network_timeout);
    sock.connect(new InetSocketAddress(ip_addr, port), ClientGlobal.g_connect_timeout)
    sock
  }

  def getSocket(addr: InetSocketAddress ) = {
    val sock = new Socket()
    sock.setSoTimeout(ClientGlobal.g_network_timeout)
    sock.connect(addr, ClientGlobal.g_connect_timeout)
    sock
  }


}
class StorageClient(trackerServer: TrackerServer , var storageServer: StorageServer ) {
  var errno = 0
  def upload_file(local_filename: String , file_ext_name: String , meta_list: Array[NameValuePair]):(String, String)  = {
    this.upload_file(null, local_filename, file_ext_name, meta_list)
  }

  def upload_file(group_name: String , local_filename: String , file_ext_name: String , meta_list: Array[NameValuePair]): (String, String)  = {
    val cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE
    this.upload_file(cmd, group_name, local_filename, file_ext_name, meta_list)
  }

  def upload_file(group_name: String, fis: InputStream, length: Long, file_ext_name: String, meta_list: Array[NameValuePair]):(String, String) = {
    val cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE
    this.do_upload_file(cmd, group_name, null, null, file_ext_name,
      length, new UploadStream(fis, length), meta_list)
  }

  def  upload_file(f: File, file_ext_name: String):(String, String) = {
    val fis = new FileInputStream(f)
    val cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE

    try {
      this.do_upload_file(cmd, null, null, null, file_ext_name,
        f.length(), new UploadStream(fis, f.length()), null)
    }
    finally {
      fis.close()
    }
  }

  def  upload_file(cmd: Byte, group_name: String, local_filename: String, file_ext_name: String, meta_list: Array[NameValuePair]):(String, String) = {
    val f = new File(local_filename)
    val fis = new FileInputStream(f)

    val ext_name: String = if (file_ext_name == null) {
      val nPos = local_filename.lastIndexOf('.')
      if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
        local_filename.substring(nPos+1)
      }
      else
        file_ext_name
    }
    else
      file_ext_name

    try {
      this.do_upload_file(cmd, group_name, null, null, ext_name,
        f.length(), new UploadStream(fis, f.length()), meta_list)
    }
    finally {
      fis.close()
    }
  }

  def newUpdatableStorageConnection(group_name: String , remote_filename: String ) = {
    if (this.storageServer != null) {
      false
    }
    else {
      val tracker = new TrackerClient()
      this.storageServer = tracker.getUpdateStorage(this.trackerServer, group_name, remote_filename);
      if (this.storageServer == null) {
        throw new FastDFSException("getStoreStorage fail, errno code: " + tracker.errno);
      }
      true
    }
  }

  def  newWritableStorageConnection(group_name: String ):Boolean = {
    if (this.storageServer != null) {
      false
    }
    else {
      val tracker = new TrackerClient();
      this.storageServer = tracker.getStoreStorage(this.trackerServer, group_name)
      if (this.storageServer == null) {
        new FastDFSException("getStoreStorage fail, errno code: " + tracker.errno)
      }
      true
    }
  }


  def do_upload_file(cmd: Byte, group_name: String , master_filename: String ,
    prefix_name: String , file_ext_name: String , file_size: Long , callback: UploadCallback ,
    meta_list: Array[NameValuePair]):(String, String) = {
    val header:Array[Byte]= null
    //val ext_name_bs:Array[Byte]= null


    val bUploadSlave = false

    val bNewConnection = if((group_name != null && group_name.length() > 0) &&
      (master_filename != null && master_filename.length() > 0) &&
      (prefix_name != null)) {
        this.newUpdatableStorageConnection(group_name, master_filename)
    }
    else {
      this.newWritableStorageConnection(group_name)
    }

    val storageSocket = this.storageServer.socket
    try {
      val ext_name_bs = Array.fill[Byte](ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN)(0)
        if (file_ext_name != null && file_ext_name.length() > 0) {
        val bs = file_ext_name.getBytes(ClientGlobal.g_charset)

        val ext_name_len = bs.length match {
          case x if x > ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN =>
            ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN;
          case y =>
            y
        }
        System.arraycopy(bs, 0, ext_name_bs, 0, ext_name_len)
      }
      val (masterFilenameBytes, sizeBytes, body_len, offset )=
      if (bUploadSlave) {
        val fileNameBytes = master_filename.getBytes(ClientGlobal.g_charset)

        val sizeBytes = new Array[Byte](2 * ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE)
        val body_len = sizeBytes.length + ProtoCommon.FDFS_FILE_PREFIX_MAX_LEN +
          ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + fileNameBytes.length + file_size

        val hexLenBytes = ProtoCommon.long2buff(master_filename.length())
        System.arraycopy(hexLenBytes, 0, sizeBytes, 0, hexLenBytes.length)
        (fileNameBytes, sizeBytes, body_len, hexLenBytes.length)
      }
      else {
        val sizeBytes = new Array[Byte](1 + ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE)
        val body_len = sizeBytes.length + ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + file_size

        sizeBytes(0) = this.storageServer.store_path.toByte
        (null, sizeBytes, body_len, 1)
      }

      val hexLenBytes = ProtoCommon.long2buff(file_size)
      System.arraycopy(hexLenBytes, 0, sizeBytes, offset, hexLenBytes.length)

      val out = storageSocket.getOutputStream()
      val header = ProtoCommon.packHeader(cmd, body_len, 0)
      val wholePkg = new Array[Byte]((header.length + body_len - file_size).toInt)
      System.arraycopy(header, 0, wholePkg, 0, header.length)
      System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length)
      var current_offset = header.length + sizeBytes.length
      if (bUploadSlave) {
        val prefix_name_bs = Array.fill[Byte](ProtoCommon.FDFS_FILE_PREFIX_MAX_LEN)(0)

        val bs = prefix_name.getBytes(ClientGlobal.g_charset)
        val prefix_name_len = bs.length

        prefix_name_len match {
          case x if x > ProtoCommon.FDFS_FILE_PREFIX_MAX_LEN =>
            ProtoCommon.FDFS_FILE_PREFIX_MAX_LEN
          case y =>
            y
        }
        if (prefix_name_len > 0) {
          System.arraycopy(bs, 0, prefix_name_bs, 0, prefix_name_len)
        }

        System.arraycopy(prefix_name_bs, 0, wholePkg, current_offset, prefix_name_bs.length)
        current_offset += prefix_name_bs.length
      }

      System.arraycopy(ext_name_bs, 0, wholePkg, current_offset, ext_name_bs.length)
        current_offset += ext_name_bs.length

      if (bUploadSlave) {
        System.arraycopy(masterFilenameBytes, 0, wholePkg, current_offset, masterFilenameBytes.length)
        current_offset += masterFilenameBytes.length
      }

      out.write(wholePkg)

      this.errno = callback.send(out)
      if (this.errno == 0) {

        val pkgInfo = ProtoCommon.recvPackage(storageSocket.getInputStream(),
          ProtoCommon.STORAGE_PROTO_CMD_RESP, -1)
        this.errno = pkgInfo.errno
        if (pkgInfo.errno == 0) {

          if (pkgInfo.body.length <= ProtoCommon.FDFS_GROUP_NAME_MAX_LEN) {
            throw new FastDFSException("body length: " + pkgInfo.body.length + " <= " + ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
          }

          val new_group_name = new String(pkgInfo.body, 0, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN).trim()
          val remote_filename = new String(pkgInfo.body, ProtoCommon.FDFS_GROUP_NAME_MAX_LEN, pkgInfo.body.length - ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)
          val results:(String, String) = (new_group_name, remote_filename)

          if (meta_list == null || meta_list.length == 0) {
            results
          }
          else {
            val result = try {
              this.set_metadata(new_group_name, remote_filename,
                meta_list, ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE)
            }
            catch {
              case ex: IOException =>
                5
            }
            if (result != 0) {
              this.errno = result
              this.delete_file(new_group_name, remote_filename)
              null
            }
            else
              results
          }
        }
        else
          null
      }
      else
        null
    }
    catch {
      case ex: IOException =>
        if (!bNewConnection) {
          try {
            this.storageServer.close()
          }
          catch {
            case ex1: IOException =>
              ex1.printStackTrace()
          }
          finally
          {
            this.storageServer = null
          }
        }
        throw ex
    }
    finally {
      if (bNewConnection) {
        try {
          this.storageServer.close()
        }
        catch {
          case ex1: IOException =>
          ex1.printStackTrace()
        }
        finally {
          this.storageServer = null
        }
      }
    }
  }


  def set_metadata(group_name: String , remote_filename: String , meta_list: Array[NameValuePair], op_flag: Byte) = {
    val bNewConnection = this.newUpdatableStorageConnection(group_name, remote_filename)
    val storageSocket = this.storageServer.socket

    try {
      val meta_buff = if (meta_list == null) {
          new Array[Byte](0)
        }
        else {
          ProtoCommon.pack_metadata(meta_list).getBytes(ClientGlobal.g_charset)
        }

      val filenameBytes = remote_filename.getBytes(ClientGlobal.g_charset)
      val sizeBytes = Array.fill[Byte](2 * ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE)(0)

      val bs = ProtoCommon.long2buff(filenameBytes.length)
      System.arraycopy(bs, 0, sizeBytes, 0, bs.length)
      val buffers = ProtoCommon.long2buff(meta_buff.length)

      System.arraycopy(buffers, 0, sizeBytes, ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE, bs.length)

      val groupBytes = Array.fill[Byte](ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)(0)
      val bus = group_name.getBytes(ClientGlobal.g_charset)

      val groupLen  = if (bs.length <= groupBytes.length) {
        bus.length
      }
      else
        groupBytes.length
      System.arraycopy(bus, 0, groupBytes, 0, groupLen)

      val header = ProtoCommon.packHeader(ProtoCommon.STORAGE_PROTO_CMD_SET_METADATA,
        2 * ProtoCommon.FDFS_PROTO_PKG_LEN_SIZE + 1 + groupBytes.length + filenameBytes.length + meta_buff.length, 0)

      val out = storageSocket.getOutputStream()

      val wholePkg = new Array[Byte](header.length + sizeBytes.length + 1 + groupBytes.length + filenameBytes.length)

      System.arraycopy(header, 0, wholePkg, 0, header.length)
      System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length)
      wholePkg(header.length+sizeBytes.length) = op_flag
      System.arraycopy(groupBytes, 0, wholePkg, header.length+sizeBytes.length+1, groupBytes.length)
      System.arraycopy(filenameBytes, 0, wholePkg, header.length+sizeBytes.length+1+groupBytes.length, filenameBytes.length)
      out.write(wholePkg)
      if (meta_buff.length > 0) {
        out.write(meta_buff)
      }

      val pkgInfo = ProtoCommon.recvPackage(storageSocket.getInputStream(), ProtoCommon.STORAGE_PROTO_CMD_RESP, 0)

      this.errno = pkgInfo.errno
      pkgInfo.errno

    }
    catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
  }

  def delete_file(group_name: String , remote_filename: String ) =  {
    val bNewConnection = this.newUpdatableStorageConnection(group_name, remote_filename);
    val storageSocket = this.storageServer.socket

    try {
      this.send_package(ProtoCommon.STORAGE_PROTO_CMD_DELETE_FILE, group_name, remote_filename);
      val pkgInfo = ProtoCommon.recvPackage(storageSocket.getInputStream(),
        ProtoCommon.STORAGE_PROTO_CMD_RESP, 0)

      this.errno = pkgInfo.errno
      pkgInfo.errno
    }
    catch {
      case ex: IOException =>
      if (!bNewConnection) {
        try {
          this.storageServer.close()
        }
        catch {
          case ex1: IOException =>
          ex1.printStackTrace()
        }
        finally {
          this.storageServer = null
        }
      }
      throw ex;
    }
    finally {
      if (bNewConnection) {
        try {
          this.storageServer.close()
        }
        catch {
          case ex1: IOException =>
            ex1.printStackTrace()
        }
        finally {
          this.storageServer = null
        }
      }
    }
  }

  def send_package(cmd: Byte, group_name: String , remote_filename: String ) = {
      val groupBytes = Array.fill[Byte](ProtoCommon.FDFS_GROUP_NAME_MAX_LEN)(0)
      val bs = group_name.getBytes(ClientGlobal.g_charset)
      val filenameBytes = remote_filename.getBytes(ClientGlobal.g_charset)

    val groupLen =  if (bs.length <= groupBytes.length) {
        bs.length
      }
      else {
        groupBytes.length
      }
    System.arraycopy(bs, 0, groupBytes, 0, groupLen)

    val header = ProtoCommon.packHeader(cmd, groupBytes.length + filenameBytes.length, 0)
    val wholePkg = new Array[Byte](header.length + groupBytes.length + filenameBytes.length)
    System.arraycopy(header, 0, wholePkg, 0, header.length)
    System.arraycopy(groupBytes, 0, wholePkg, header.length, groupBytes.length)
    System.arraycopy(filenameBytes, 0, wholePkg, header.length+groupBytes.length, filenameBytes.length)
    this.storageServer.socket.getOutputStream().write(wholePkg)
  }


}
