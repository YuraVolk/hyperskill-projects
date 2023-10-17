package gitinternals

import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.Inflater

class GitManager(private val location: String) {
    fun print() {
        var outputStream: ByteArrayOutputStream? = null
        try {
            val compressedData = Files.readAllBytes(Paths.get(location))
            val inflater = Inflater().also { it.setInput(compressedData) }
            outputStream = ByteArrayOutputStream(compressedData.size); val buffer = ByteArray(1024)
            while (!inflater.finished()) outputStream.write(buffer, 0, inflater.inflate(buffer))
            outputStream.close()
            inflater.end()

            val contentBytes = outputStream.toByteArray(); val content = StringBuilder()
            contentBytes.indices.forEach {
                content.append(if (contentBytes[it] == 0.toByte()) "\n" else contentBytes[it].toInt().toChar()) }
            println(content)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally { outputStream?.close() }
    }
}

fun main() {
    println("Enter git object location:").also { GitManager(readln()).print() }
}
