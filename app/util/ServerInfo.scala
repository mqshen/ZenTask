package util

import java.net.{InetSocketAddress, Socket}

/**
 * Created by GoldRatio on 4/18/14.
 */
class ServerInfo(val ip_addr: String , val port: Int) {
  def connect() = {
    val sock = new Socket()
    sock.setReuseAddress(true)
    sock.setSoTimeout(ClientGlobal.g_network_timeout)
    sock.connect(new InetSocketAddress(this.ip_addr, this.port), ClientGlobal.g_connect_timeout)
    sock
  }


}
