package jsondatabase.server

import jsondatabase.Request
import jsondatabase.ServerClientContract
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class Server : ServerClientContract {
    companion object {
        const val ADDRESS = "127.0.0.1"
        const val PORT = 23456
    }

    init {
        println("Server started!")
    }

    private val database = JSONDatabase()
    private val serverSocket = ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))
    override lateinit var socket: Socket
    override lateinit var input: DataInputStream
    override lateinit var output: DataOutputStream

    fun acceptRequest() {
        while (true) {
            socket = serverSocket.accept()
            input = DataInputStream(socket.getInputStream())
            output = DataOutputStream(socket.getOutputStream())
            val request = Json.decodeFromString<Request>(input.readUTF())
            output.writeUTF(Json.encodeToString(
                when (request.type) {
                    "get" -> database[request.key]
                    "set" -> database.setValue(request.key, request.value)
                    "delete" -> database.delete(request.key)
                    "exit" -> ServerResponse(OK)
                    else -> ServerResponse(ERROR)
                }
            ))

            if (request.type == "exit") {
                socket.close()
                serverSocket.close()
                break
            }
        }
    }
}

fun main() = Server().acceptRequest()
