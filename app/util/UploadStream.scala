package util

import java.io.{OutputStream, InputStream}

/**
 * Created by GoldRatio on 4/18/14.
 */
class UploadStream(inputStream: InputStream, fileSize: Long) extends UploadCallback {

  /**
   * send file content callback function, be called only once when the file uploaded
   * @param out output stream for writing file content
   * @return 0 success, return none zero(errno) if fail
   */
  def send(out: OutputStream):Int = {
    val buff = new Array[Byte](256 * 1024)

    def readBytes(remainBytes: Long): Int = {
      val size = if(remainBytes > buff.length) buff.length else remainBytes
      val bytes = inputStream.read(buff, 0, size.toInt)
      if(bytes < 0) {
        -1
      }
      else {
        val remain = remainBytes - bytes
        if(remain < 0)
          -1
        else {
          out.write(buff, 0, bytes)
          if(remain == 0)
            0
          else
            readBytes(remain)
        }
      }
    }
    readBytes(fileSize)
  }
}

