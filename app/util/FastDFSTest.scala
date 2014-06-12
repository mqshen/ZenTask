package util

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

/**
 * Created by GoldRatio on 4/18/14.
 */
object FastDFSTest {

  def main(args: Array[String]) {
    val tracker = new TrackerClient()
    val trackerServer = tracker.getConnection()

    val  client = new StorageClient(trackerServer, null)

    System.out.println(client.upload_file("fdfs_client.conf" , null , null))
  }

}
