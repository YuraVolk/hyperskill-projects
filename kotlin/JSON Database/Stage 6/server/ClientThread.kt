package jsondatabase.server

import jsondatabase.Request
import jsondatabase.ServerClientContract
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ClientThread(override val socket: Socket, private val server: Server) : ServerClientContract {
    override val input: DataInputStream = DataInputStream(socket.getInputStream())
    override val output: DataOutputStream = DataOutputStream(socket.getOutputStream())

    init {
        try {
            this.run()
        } catch (_: IOException) { }
    }

    private fun run() {
        val request = Json.decodeFromString<Request>(input.readUTF())
        output.writeUTF(Json.encodeToString(
            when (request.type) {
                "get" -> server.database[request.key]
                "set" -> server.database.setValue(request.key, request.value)
                "delete" -> server.database.delete(request.key)
                "exit" -> ServerResponse(OK)
                else -> ServerResponse(ERROR)
            }
        ))

        if (request.type == "exit") socket.close().run { server.stop() }
    }
}
