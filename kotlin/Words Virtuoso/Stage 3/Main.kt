package wordsvirtuoso

import java.io.File

fun validateWord(inputString: String) = "^(?!.*(.).*\\1)[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE).matchEntire(inputString) != null
fun inputWordsList(filename: String, title: String): List<String>? {
    val inputWords = File(filename).let { if (it.exists()) it.readLines() else println("Error: The $title file ${it.name} doesn't exist.").let { null } }
        ?: return null
    val filteredWords = inputWords.filter(::validateWord).map(String::lowercase)
    return if (inputWords.size == filteredWords.size) { filteredWords } else {
        println("Error: ${inputWords.size - filteredWords.size} invalid words were found in the $filename file.")
        null
    }
}

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Error: Wrong number of arguments.")
        return
    }
    val words = inputWordsList(args.first(), "words") ?: return
    val candidates = inputWordsList(args.last(), "candidate words") ?: return
    val difference = candidates.size - candidates.count { words.contains(it) }
    if (difference != 0) {
        println("Error: $difference candidate words are not included in the ${args.first()} file.")
        return
    }
    println("Words Virtuoso")
}
