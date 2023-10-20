package jsondatabase

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class Request(val type: String, @SerialName("key") val key: JsonElement? = null, @SerialName("value") val value: JsonElement? = null) {
    companion object {
        private const val FILES_FOLDER = "src/jsondatabase/client/data/"

        fun fromArguments(args: Array<String>): Request {
            val (type, index, value, inputFile) = listOf("t", "k", "v", "in").map {
                Regex("(?<=-$it).+?(?=-|\$)").findAll(args.joinToString(" ")).lastOrNull()?.value?.trim()
            }
            val finalIndex = if (index != null && (index.startsWith("'") || index.startsWith("\"") || index.endsWith("\"") || index.endsWith("'")))
                index else "\"$index\""

            return if (inputFile != null) {
                Json.decodeFromString<Request>(File("$FILES_FOLDER$inputFile").readText())
            } else if (index == null) {
                Request(type ?: "")
            } else if (value == null) {
                Request(type ?: "", Json.decodeFromString<JsonElement>(finalIndex))
            } else {
                val newValue = try {
                    Json.decodeFromString<JsonElement>(value)
                } catch (e: Exception) { Json.decodeFromString<JsonElement>("\"$value\"") }
                Request(type ?: "", Json.decodeFromString<JsonElement>(finalIndex), newValue)
            }
        }
    }
}
