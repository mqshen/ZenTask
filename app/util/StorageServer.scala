package util

import java.net.InetSocketAddress

/**
 * Created by GoldRatio on 4/18/14.
 */
class StorageServer(ip_addr: String , port: Int, val store_path: Int)
  extends TrackerServer(ClientGlobal.getSocket(ip_addr, port), new InetSocketAddress(ip_addr, port)){

}
