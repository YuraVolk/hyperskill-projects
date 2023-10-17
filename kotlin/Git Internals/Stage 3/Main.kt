package gitinternals

import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.io.FileInputStream
import java.util.zip.InflaterInputStream
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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

    fun printType() {
        val (type, innerContent) = getFileContents().let {
            listOf(it.substring(0, it.indexOf('\u0000')).split(" ")[0], it.drop(it.indexOf('\u0000') + 1)) }
        println("*${type.uppercase()}*\n" + when (type) {
            "blob" -> innerContent
            "commit" -> decodeCommit(innerContent)
            else -> ""
        })
    }
}

fun main() = GitManager().printType()
