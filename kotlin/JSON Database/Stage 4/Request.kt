package jsondatabase;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(val type: String, @SerialName("key") val key: String? = null, @SerialName("value") val value: String? = null) {
    companion object {
        fun fromArguments(args: Array<String>): Request {
            val (type, index, value) = listOf("t", "k", "v").map {
                Regex("(?<=-$it).+?(?=-|\$)").findAll(args.joinToString(" ")).lastOrNull()?.value?.trim()
            }

            return if (index == null) {
                Request(type ?: "")
            } else if (value == null) {
                Request(type ?: "", index)
            } else Request(type ?: "", index, value)
        }
    }
}
