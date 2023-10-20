package jsondatabase.server

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

const val ERROR = "ERROR"
const val OK = "OK"
const val NO_KEY = "No such key"

@Serializable
data class ServerResponse(val response: String, val reason: String? = null, val value: String? = null)

class JSONDatabase(private val server: Server) {
    private val databasePath = "JSON Database (Kotlin)/task/src/jsondatabase/server/data/db.json"

    init {
        if (!Files.exists(Paths.get(databasePath))) {
            File(databasePath).also {
                it.parentFile.mkdirs()
                it.createNewFile()
                it.writeText("{}")
            }
        }
    }

    private fun extractItems(): MutableMap<String, String> {
        val resultingMap: MutableMap<String, String> = mutableMapOf()
        server.readLock.lock().let {
            val text = File(databasePath).readText()
            server.readLock.unlock()
            val result = Json.parseToJsonElement(text)
            require(result is JsonObject)
            result
        }.mapValuesTo(resultingMap) { it.component2().toString().drop(1).dropLast(1) }
        return resultingMap;
    }

    private fun writeItems(items: MutableMap<String, String>) {
        server.writeLock.lock().let { File(databasePath).writeText(Json.encodeToString(items)) }
        server.writeLock.unlock()
    }

    fun setValue(index: String?, data: String?): ServerResponse {
        val items = extractItems()
        if (data == null || index == null) return ServerResponse(ERROR)
        items[index] = data
        writeItems(items)
        return ServerResponse(OK)
    }

    operator fun get(index: String?): ServerResponse {
        val items = extractItems()
        return if (!items.containsKey(index)) ServerResponse(ERROR, reason = NO_KEY) else ServerResponse(OK, value = items[index])
    }

    fun delete(index: String?): ServerResponse {
        val items = extractItems()
        val response = if (items.remove(index) != null) ServerResponse(OK) else ServerResponse(ERROR, reason = NO_KEY)
        if (response.response == OK) writeItems(items)
        return response
    }
}
