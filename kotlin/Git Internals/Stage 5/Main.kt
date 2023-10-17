package gitinternals

import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.io.File
import java.io.FileInputStream
import java.util.zip.InflaterInputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class GitManager {
    private val location: String

    init {
        println("Enter .git directory location:")
        location = readln().also { println("Enter command:") }
        when (readln()) {
            "list-branches" -> listBranches()
            "cat-file" -> println("Enter git object hash:").also { catFile(getFileContents(readln())) }
        }
    }

    private fun getFileContents(objectHash: String) = InflaterInputStream(FileInputStream("$location/objects/${objectHash.take(2)}/${objectHash.drop(2)}"))
            .readAllBytes()

    private fun changeDataToReadable(string: String, prefix: String): String {
        val (beforeDate, afterDate) = string.split("(?<=>)\\s".toRegex())
        val (epoch, diff) = afterDate.split(" ")
        val finalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX").withZone(ZoneOffset.of(diff)).format(
            Instant.ofEpochSecond(epoch.toLong()).toKotlinInstant().toJavaInstant())
        return "${beforeDate.replace("[<>]".toRegex(), "")} $prefix timestamp: $finalDate"
    }

    private fun decodeCommit(content: String): String {
        var committer = ""; var author = ""; var tree = ""
        val parents = mutableListOf<String>()
        var commitMessage = ""

        content.split("\n").map { it.trim() }.filter { it.isNotBlank() && it.isNotEmpty() }.forEach {
            when (true) {
                it.startsWith("tree") -> tree = it.drop("tree".length + 1)
                it.startsWith("parent") -> parents.add(it.drop("author".length + 1))
                it.startsWith("author") -> author = changeDataToReadable(it.drop("author".length + 1), "original")
                it.startsWith("committer") -> committer = changeDataToReadable(it.drop("committer".length + 1), "commit")
                else -> commitMessage += "$it\n"
            }
        }

        return "tree: $tree\n${if (parents.size > 0) "parents: " else ""}${parents.joinToString(separator = " | ")}" +
                "${if (parents.size > 0) "\n" else ""}author: $author\ncommitter: $committer\ncommit message:\n${commitMessage.trim()}"
    }

    private fun decodeTree(content: List<Byte>): String {
        var result = ""; var fileInfo = ""
        var count = 0; var byteCount = 0
        content.forEach { when (count) {
            0 -> {
                if (it.toInt().toChar() == ' ') count++
                result += it.toInt().toChar().toString()
            }
            1 -> if (it.toInt().toChar() == 0.toChar()) count++ else fileInfo += it.toInt().toChar().toString()
            2 -> {
                result += String.format("%02x", it).lowercase()
                if (++byteCount == 20) {
                    result += " $fileInfo\n"; fileInfo = ""
                    count = 0; byteCount = 0
                }
            }
        } }

        return result
    }

    private fun catFile(fileContents: ByteArray) {
        val stringContents = fileContents.toString(Charsets.UTF_8)
        val (type, innerContent) = stringContents.let {
            listOf(it.substring(0, it.indexOf('\u0000')).split(" ")[0], it.drop(it.indexOf('\u0000') + 1)) }
        println("*${type.uppercase()}*\n" + when (type) {
            "blob" -> innerContent
            "commit" -> decodeCommit(innerContent)
            "tree" -> decodeTree(fileContents.drop(stringContents.indexOf('\u0000') + 1))
            else -> ""
        })
    }

    private fun listBranches() {
        val head = File("$location/HEAD").readText().split("/").last().trim()
        val branches = File("$location/refs/heads").list()?.sorted()
        println(branches?.joinToString("\n") { if (it == head) "* $it" else "  $it" } ?: "No branches found")
    }
}

fun main() {
    GitManager()
}
