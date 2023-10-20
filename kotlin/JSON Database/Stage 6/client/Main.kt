package jsondatabase.client

import jsondatabase.Request
import jsondatabase.ServerClientContract
import jsondatabase.server.Server
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class Client : ServerClientContract {
    override var socket = Socket(InetAddress.getByName(Server.ADDRESS), Server.PORT).also { println("Client started!") }
    override var input = DataInputStream(socket.getInputStream())
    override var output: DataOutputStream = DataOutputStream(socket.getOutputStream())

    fun sendRequest(args: Array<String>) {
        output.writeUTF(Json.encodeToString(Request.fromArguments(args)).also { println("Sent: $it") })
        println("Received: ${input.readUTF()}")
        socket.close()
    }
}

fun main(args: Array<String>) = Client().sendRequest(args)
