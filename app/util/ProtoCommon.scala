package util

import java.io.{IOException, InputStream}
import java.net.Socket
import org.apache.http.NameValuePair

/**
 * Created by GoldRatio on 4/18/14.
 */
case class RecvPackageInfo(errno: Byte, body: Array[Byte]) {

}

case class RecvHeaderInfo(errno: Byte, body_len: Long) {
}


object ProtoCommon {


  val FDFS_FILE_EXT_NAME_MAX_LEN:Byte  = 6
  val STORAGE_PROTO_CMD_UPLOAD_FILE:Byte = 11
  val STORAGE_PROTO_CMD_DELETE_FILE:Byte	= 12
  val STORAGE_PROTO_CMD_SET_METADATA:Byte	 = 13
  val FDFS_FILE_PREFIX_MAX_LEN:Byte    = 16
  val FDFS_PROTO_CMD_QUIT:Byte      = 82
  val TRACKER_PROTO_CMD_RESP:Byte = 100
  val TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE:Byte = 101
  val TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE:Byte = 104

  val FDFS_IPADDR_SIZE	 = 16


  val TRACKER_QUERY_STORAGE_FETCH_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1 + FDFS_PROTO_PKG_LEN_SIZE
  val FDFS_GROUP_NAME_MAX_LEN  = 16

  val STORAGE_PROTO_CMD_RESP	 = TRACKER_PROTO_CMD_RESP

  val FDFS_PROTO_PKG_LEN_SIZE	= 8
  val TRACKER_QUERY_STORAGE_STORE_BODY_LEN = FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE + FDFS_PROTO_PKG_LEN_SIZE

  val TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE:Byte = 103

  val PROTO_HEADER_CMD_INDEX	   = FDFS_PROTO_PKG_LEN_SIZE
  val PROTO_HEADER_STATUS_INDEX = FDFS_PROTO_PKG_LEN_SIZE+1

  val FDFS_RECORD_SEPERATOR	= "\u0001"
  val FDFS_FIELD_SEPERATOR	  = "\u0002"


  val STORAGE_SET_METADATA_FLAG_OVERWRITE:Byte = 'O'

  def long2buff(n: Long ) = {
    val bs = new Array[Byte](8)
    bs(0) = ((n >> 56) & 0xFF).toByte
    bs(1) = ((n >> 48) & 0xFF).toByte
    bs(2) = ((n >> 40) & 0xFF).toByte
    bs(3) = ((n >> 32) & 0xFF).toByte
    bs(4) = ((n >> 24) & 0xFF).toByte
    bs(5) = ((n >> 16) & 0xFF).toByte
    bs(6) = ((n >> 8) & 0xFF).toByte
    bs(7) = (n & 0xFF).toByte
    bs
  }

  def packHeader(cmd: Byte , pkg_len: Long , errno: Byte) = {

    val header = Array.fill[Byte](FDFS_PROTO_PKG_LEN_SIZE + 2)(0)
    val hex_len = ProtoCommon.long2buff(pkg_len)
    System.arraycopy(hex_len, 0, header, 0, hex_len.length)
    header(PROTO_HEADER_CMD_INDEX) = cmd
    header(PROTO_HEADER_STATUS_INDEX) = errno
    header
  }

  def recvPackage(in: InputStream , expect_cmd: Byte, expect_body_len: Long):RecvPackageInfo = {
    val header = recvHeader(in, expect_cmd, expect_body_len)
    if (header.errno != 0) {
      new RecvPackageInfo(header.errno, null)
    }

    val body = new Array[Byte](header.body_len.toInt)

    def readByte(totalBytes: Int, remainBytes: Int):(Int, Int) = {
      val bytes = in.read(body, totalBytes, remainBytes)
      if(bytes <= 0)
        (totalBytes, remainBytes)
      else
        readByte(totalBytes + bytes, remainBytes - bytes)
    }

    val (totalBytes, remainBytes) = readByte(0, header.body_len.toInt)


    if (totalBytes != header.body_len) {
      throw new IOException("recv package size " + totalBytes + " != " + header.body_len)
    }

    new RecvPackageInfo(0, body)
  }

  def recvHeader(in: InputStream , expect_cmd: Byte, expect_body_len: Long): RecvHeaderInfo = {

    val header = new Array[Byte](FDFS_PROTO_PKG_LEN_SIZE + 2)

    val bytes = in.read(header)
    if (bytes != header.length) {
      throw new IOException("recv package size " + bytes + " != " + header.length)
    }

    if (header(PROTO_HEADER_CMD_INDEX) != expect_cmd) {
      throw new IOException("recv cmd: " + header(PROTO_HEADER_CMD_INDEX) + " is not correct, expect cmd: " + expect_cmd);
    }

    if (header(PROTO_HEADER_STATUS_INDEX) != 0) {
      new RecvHeaderInfo(header(PROTO_HEADER_STATUS_INDEX), 0)
    }
    else {
      val pkg_len = ProtoCommon.buff2long(header, 0)
      if (pkg_len < 0) {
        throw new IOException("recv body length: " + pkg_len + " < 0!")
      }

      if (expect_body_len >= 0 && pkg_len != expect_body_len) {
        throw new IOException("recv body length: " + pkg_len + " is not correct, expect length: " + expect_body_len)
      }
      else
        new RecvHeaderInfo(0, pkg_len)
    }


  }


  def buff2long(bs: Array[Byte], offset: Int)  = {
    (if(bs(offset) >= 0 ) bs(offset) else 256 + bs(offset)) << 56 |
      (if(bs(offset + 1) >= 0 ) bs(offset + 1) else 256 + bs(offset + 1)) << 48 |
      (if(bs(offset + 2) >= 0 ) bs(offset + 2) else 256 + bs(offset + 2)) << 40 |
      (if(bs(offset + 3) >= 0 ) bs(offset + 3) else 256 + bs(offset + 3)) << 32 |
      (if(bs(offset + 4) >= 0 ) bs(offset + 4) else 256 + bs(offset + 4)) << 24 |
      (if(bs(offset + 5) >= 0 ) bs(offset + 5) else 256 + bs(offset + 5)) << 16 |
      (if(bs(offset + 6) >= 0 ) bs(offset + 6) else 256 + bs(offset + 6)) << 8 |
      (if(bs(offset + 7) >= 0 ) bs(offset + 7) else 256 + bs(offset + 7) )
  }

  def closeSocket(sock: Socket ) {
    val header = packHeader(FDFS_PROTO_CMD_QUIT, 0, 0)
    sock.getOutputStream().write(header)
    sock.close()
  }


  def pack_metadata(meta_list: Array[NameValuePair]) =  {
    if (meta_list.length == 0) {
      ""
    }
    else {
      val sb = new StringBuffer(32 * meta_list.length)
      sb.append(meta_list(0).getName()).append(FDFS_FIELD_SEPERATOR).append(meta_list(0).getValue())
      (1 until meta_list.length).foreach { i =>
        sb.append(FDFS_RECORD_SEPERATOR);
        sb.append(meta_list(i).getName()).append(FDFS_FIELD_SEPERATOR).append(meta_list(i).getValue())
      }

      sb.toString()
    }

  }


}
