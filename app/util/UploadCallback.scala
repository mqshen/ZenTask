package util

import java.io.OutputStream

/**
 * Created by GoldRatio on 4/18/14.
 */
trait UploadCallback {

  def send(out: OutputStream ): Int

}
