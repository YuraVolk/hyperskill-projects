package jsondatabase

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class Request(val type: String, @SerialName("key") val key: String? = null, @SerialName("value") val value: String? = null) {
    companion object {
        private const val FILES_FOLDER = "src/jsondatabase/client/data/"

        fun fromArguments(args: Array<String>): Request {
            val (type, index, value, inputFile) = listOf("t", "k", "v", "in").map {
                Regex("(?<=-$it).+?(?=-|\$)").findAll(args.joinToString(" ")).lastOrNull()?.value?.trim()
            }

            return if (inputFile != null) {
                Json.decodeFromString<Request>(File("$FILES_FOLDER$inputFile").readText())
            } else if (index == null) {
                Request(type ?: "")
            } else if (value == null) {
                Request(type ?: "", index)
            } else Request(type ?: "", index, value)
        }
    }
}
