package util

import java.net.{Socket, InetSocketAddress}
import java.io.IOException

/**
 * Created by GoldRatio on 4/18/14.
 */
class TrackerGroup(tracker_servers: Array[InetSocketAddress]) {
  val lock = new Integer(0)
  var tracker_server_index = 0

  def getConnection(serverIndex: Int) = {
    val sock = new Socket()
    sock.setReuseAddress(true);
    sock.setSoTimeout(ClientGlobal.g_network_timeout)
    sock.connect(this.tracker_servers(serverIndex), ClientGlobal.g_connect_timeout);
    TrackerServer(sock, this.tracker_servers(serverIndex))
  }


  def getConnection():TrackerServer = {

    val current_index = this.lock.synchronized {
      this.tracker_server_index += 1
      if (this.tracker_server_index >= this.tracker_servers.length) {
        this.tracker_server_index = 0
      }
      this.tracker_server_index
    }

    try {
      this.getConnection(current_index)
    }
    catch {
      case ex: IOException => {
        System.err.println("connect to server " + this.tracker_servers(current_index).getAddress().getHostAddress() + ":" + this.tracker_servers(current_index).getPort() + " fail")
        ex.printStackTrace(System.err)
        def getConnectionFoIndex(index: Int):TrackerServer = {
          if( index < this.tracker_servers.length) {
            if (index != current_index) {
              try {
                val trackerServer = this.getConnection(index)

                this.lock.synchronized {
                  if (this.tracker_server_index == current_index) {
                    this.tracker_server_index = index
                  }
                }
                trackerServer
              }
              catch {
                case ex: IOException =>
                  System.err.println("connect to server " + this.tracker_servers(index).getAddress().getHostAddress() + ":" + this.tracker_servers(index).getPort() + " fail")
                  ex.printStackTrace(System.err)
                  getConnectionFoIndex(index + 1)
              }
            }
            else {
              getConnectionFoIndex(index + 1)
            }
          }
          else
            null
        }
        getConnectionFoIndex(0)
      }
    }

  }



}

object TrackerServer {

  def apply(inetSockAddr: InetSocketAddress) = {
    val sock = ClientGlobal.getSocket(inetSockAddr)
    new TrackerServer(sock, inetSockAddr)
  }

  def apply(sock: Socket, inetSockAddr: InetSocketAddress) = {
    new TrackerServer(sock, inetSockAddr)
  }

}

class TrackerServer(val socket: Socket ,val inetSockAddr: InetSocketAddress ) {

  def close() {
    if (this.socket != null) {
      ProtoCommon.closeSocket(this.socket)
    }
  }

}