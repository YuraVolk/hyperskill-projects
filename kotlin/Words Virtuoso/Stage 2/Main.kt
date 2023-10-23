package wordsvirtuoso

import java.io.File

fun validateWord(inputString: String) = "^(?!.*(.).*\\1)[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE).matchEntire(inputString) != null
fun main() {
    println("Input the words file:")
    val file = readln()
    val inputWords = File(file).let { if (it.exists()) it.readLines() else println("Error: The words file ${it.name} doesn't exist.").let { null } }
        ?: return
    val filteredWords = inputWords.filter(::validateWord)
    println(if (inputWords.size == filteredWords.size) "All words are valid!"
        else "Warning: ${inputWords.size - filteredWords.size} invalid words were found in the $file file.")
}
