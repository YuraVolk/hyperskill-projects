package jsondatabase.client

import jsondatabase.ServerClientContract
import jsondatabase.server.Server
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class Client : ServerClientContract {
    override var socket = Socket(InetAddress.getByName(Server.ADDRESS), Server.PORT).also { println("Client started!") }
    override var input = DataInputStream(socket.getInputStream())
    override var output: DataOutputStream = DataOutputStream(socket.getOutputStream())

    fun sendRequest() {
        "Give me a record # 12".also {
            output.writeUTF(it)
            println("Sent: $it")
        }
        println("Received: ${input.readUTF()}")
    }
}

fun main() = Client().sendRequest()