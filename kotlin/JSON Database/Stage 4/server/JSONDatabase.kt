package jsondatabase.server

import kotlinx.serialization.Serializable

const val ERROR = "ERROR"
const val OK = "OK"
const val NO_KEY = "No such key"

@Serializable
data class ServerResponse(val response: String, val reason: String? = null, val value: String? = null)

class JSONDatabase {
    private val items = mutableMapOf<String, String>()

    fun setValue(index: String?, data: String?): ServerResponse {
        if (data == null || index == null) return ServerResponse(ERROR)
        items[index] = data
        return ServerResponse(OK)
    }

    operator fun get(index: String?) = if (!items.containsKey(index)) ServerResponse(ERROR, reason = NO_KEY) else ServerResponse(OK, value = items[index])
    fun delete(index: String?) = if (items.remove(index) != null) ServerResponse(OK) else ServerResponse(ERROR, reason = NO_KEY)
}
