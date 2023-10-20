package jsondatabase.server

import jsondatabase.ServerClientContract
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class Server : ServerClientContract {
    companion object {
        const val ADDRESS = "127.0.0.1"
        const val PORT = 23456
    }

    override val socket: Socket = ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))
        .also { println("Server started!") }.run { accept() }
    override val input = DataInputStream(socket.getInputStream())
    override val output = DataOutputStream(socket.getOutputStream())

    fun acceptRequest() {
        println("Received: ${input.readUTF()}")
        "A record # 12 was sent!".also {
            output.writeUTF(it)
            println("Sent: $it")
        }
    }
}

fun main() = Server().acceptRequest()
