package jsondatabase.server

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Serializable
data class ServerResponse(val response: String, val reason: String? = null, val value: JsonElement? = null)

@Suppress("UNCHECKED_CAST")
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

    private fun extractItems(): JsonObject {
        return server.readLock.lock().let {
            val text = File(databasePath).readText()
            server.readLock.unlock()
            val result = Json.parseToJsonElement(text)
            require(result is JsonObject)
            result
        }
    }

    private fun writeItems(items: MutableMap<String, Any>) {
        server.writeLock.lock().let { File(databasePath).writeText(toJsonFromMutableMap(items).toString()) }
        server.writeLock.unlock()
    }

    private fun extractIndex(index: JsonElement?) = when (index) {
            is JsonArray -> index.map { it.jsonPrimitive.contentOrNull }
            is JsonPrimitive -> listOf(index.contentOrNull)
            else -> null
        }?.filterNotNull()

    private fun toMutableMapFromJson(json: JsonElement): Any {
        return when (json) {
            is JsonPrimitive -> json.content
            is JsonObject -> mutableMapOf<String, Any>().apply { for ((key, value) in json) { this[key] = toMutableMapFromJson(value) } }
            is JsonArray -> mutableListOf<Any>().apply { for (element in json) add(toMutableMapFromJson(element)) }
        }
    }

    private fun toJsonFromMutableMap(map: MutableMap<String, Any>): JsonObject {
        return JsonObject(map.entries.associate { (key, value) ->
            key to when (value) {
                is Map<*, *> -> toJsonFromMutableMap(value as MutableMap<String, Any>)
                else -> JsonPrimitive(value.toString())
            }
        })
    }

    fun setValue(passedIndex: JsonElement?, data: JsonElement?): ServerResponse {
        val index = extractIndex(passedIndex)
        if (data == null || index == null) return ServerResponse(ERROR)
        val items: MutableMap<String, Any> = toMutableMapFromJson(extractItems()) as? MutableMap<String, Any> ?: return ServerResponse(ERROR)

        var currentObject = items
        for (i in 0 until index.lastIndex) {
            val key = index[i]
            if (!currentObject.containsKey(key)) currentObject[key] = mutableMapOf<String, Any>()

            val value = currentObject[key]
            if (value is MutableMap<*, *>) {
                currentObject = value as? MutableMap<String, Any> ?: return ServerResponse(ERROR)
            } else return ServerResponse(ERROR, reason = NO_KEY)
        }
        currentObject[index.last()] = toMutableMapFromJson(data)

        writeItems(items)
        return ServerResponse(OK)
    }

    operator fun get(passedIndex: JsonElement?): ServerResponse {
        val index = extractIndex(passedIndex) ?: return ServerResponse(ERROR)
        var items: MutableMap<String, Any> = toMutableMapFromJson(extractItems()) as? MutableMap<String, Any> ?: return ServerResponse(ERROR)
        for (key in index) {
            if (!items.containsKey(key)) return ServerResponse(ERROR, reason = NO_KEY)
            val value = items[key]
            if (value !is MutableMap<*, *>) {
                return if (index.indexOf(key) != index.lastIndex) {
                    ServerResponse(ERROR, reason = NO_KEY)
                } else ServerResponse(OK, value = (value as? String)?.let { Json.decodeFromString<JsonElement>("\"$it\"") })
            } else items = value as? MutableMap<String, Any> ?: return ServerResponse(ERROR, reason = NO_KEY)
        }

        return ServerResponse(OK, value = toJsonFromMutableMap(items))
    }

    fun delete(passedIndex: JsonElement?): ServerResponse {
        val index = extractIndex(passedIndex) ?: return ServerResponse(ERROR)
        val items: MutableMap<String, Any> = toMutableMapFromJson(extractItems()) as? MutableMap<String, Any> ?: return ServerResponse(ERROR)

        var currentObject = items
        for (i in 0 until index.lastIndex) {
            val key = index[i]
            if (!currentObject.containsKey(key)) return ServerResponse(ERROR, reason = NO_KEY)
            val value = currentObject[key]
            if (value is MutableMap<*, *>) {
                currentObject = value as? MutableMap<String, Any> ?: return ServerResponse(ERROR)
            } else return ServerResponse(ERROR, reason = NO_KEY)
        }

        if (currentObject.remove(index.last()) == null) return ServerResponse(ERROR, reason = NO_KEY)
        writeItems(items)
        return ServerResponse(OK)
    }
}
