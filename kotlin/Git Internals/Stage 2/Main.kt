package gitinternals

import java.io.FileInputStream
import java.util.zip.InflaterInputStream

class GitManager {
    private val location: String;
    private val objectHash: String;

    init {
        println("Enter .git directory location:")
        location = readln().also { println("Enter git object hash:") }
        objectHash = readln()
    }

    private fun getFileContents() = InflaterInputStream(FileInputStream("$location/objects/${objectHash.take(2)}/${objectHash.drop(2)}"))
            .readAllBytes().toString(Charsets.UTF_8)

    fun printType() {
        val (type, length) = this.getFileContents().let { it.substring(0, it.indexOf('\u0000')).split(" ") }
        println("type:$type length:${length}")
    }
}

fun main() = GitManager().printType()
