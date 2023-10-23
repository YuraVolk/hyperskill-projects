package wordsvirtuoso

import java.io.File
import java.lang.IllegalArgumentException

class Game() {
    private var words: List<String> = listOf()
    private var candidates: List<String> = listOf()
    var tries = 1
    private val clueStrings = mutableListOf<String>()
    private val excludedLetters = sortedSetOf<String>()

    companion object {
        private fun validateWord(inputString: String) = "^(?!.*(.).*\\1)[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE).matchEntire(inputString) != null
        private fun inputWordsList(filename: String, title: String): List<String> {
            val words = File(filename).let {
                check(it.exists()) { "Error: The $title file ${it.name} doesn't exist." }
                it.readLines()
            }
            val filteredWords = words.filter(::validateWord).map(String::lowercase)
            check(words.size == filteredWords.size) { "Error: ${words.size - filteredWords.size} invalid words were found in the $filename file." }
            return filteredWords
        }
    }

    constructor(args: Array<String>) : this() {
        require(args.size == 2) { "Error: Wrong number of arguments." }
        words = inputWordsList(args.first(), "words")
        candidates = inputWordsList(args.last(), "candidate words")
        val difference = candidates.size - candidates.count { words.contains(it) }
        check(difference == 0) { "Error: $difference candidate words are not included in the ${args.first()} file." }
    }

    private fun getValidationErrors(word: String): String {
        return when (true) {
            (word.length != 5) -> "The input isn't a 5-letter word."
            (!word.matches("^[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE))) -> "One or more letters of the input aren't valid."
            (word.toSet().size != word.length) -> "The input has duplicate letters."
            (!words.contains(word)) -> "The input word isn't included in my words list."
            else -> throw IllegalArgumentException("The supplied word is valid")
        }
    }

    fun startGame() {
        val secretWord = candidates.random().lowercase().also { println("Words Virtuoso") }

        do {
            println("\nInput a 5-letter word:")
            val inputWord = readln().lowercase()
            if (inputWord == "exit") continue else if (!validateWord(inputWord) || !words.contains(inputWord)) {
                println(getValidationErrors(inputWord))
                continue
            } else if (inputWord != secretWord) tries++

            val result = (inputWord.mapIndexed { index, c -> when (true) {
                (secretWord[index] == c) -> "\u001B[48:5:10m${c.uppercase()}\u001B[0m"
                (secretWord.contains(c)) -> "\u001B[48:5:11m${c.uppercase()}\u001B[0m"
                else -> "\u001B[48:5:7m${c.uppercase()}\u001B[0m"
            } }.joinToString(""))
            excludedLetters.addAll(inputWord.filter { !secretWord.contains(it) }.uppercase().split(""))
            println()
            clueStrings.add(result).also { clueStrings.forEach(::println) }
            if (inputWord == secretWord) {
                println("\nCorrect!")
                return
            } else println("\n\u001B[48:5:14m${excludedLetters.joinToString("")}\u001B[0m")
        } while (inputWord != "exit")

        println("\nThe game is over.")
    }
}
