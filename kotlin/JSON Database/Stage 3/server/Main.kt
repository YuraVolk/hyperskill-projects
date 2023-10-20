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
            val inputData = input.readUTF()
            val (type, index, value) = listOf("t", "i", "m").map { Regex("(?<=-$it).+?(?=-|\$)").findAll(inputData).lastOrNull()?.value?.trim() }
            (index?.toInt() ?: -1).also {
                output.writeUTF(
                    when (type) {
                        "get" -> database[it]
                        "set" -> database.setValue(it, value)
                        "delete" -> database.delete(it)
                        "exit" -> "OK"
                        else -> "ERROR"
                    }
                )
            }

            if (type == "exit") {
                socket.close()
                serverSocket.close()
                break
            }
        }
    }
}

fun main() = Server().acceptRequest()
