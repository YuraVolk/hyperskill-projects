package wordsvirtuoso

fun main() {
    println("Input a 5-letter string:")
    val inputString = readln().trim()
    val isMatched = "^(?!.*(.).*\\1)[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE).matchEntire(inputString)
    if (isMatched == null) {
        if (inputString.length != 5) {
            println("The input isn't a 5-letter string.")
        } else if ("^[a-z]{5}\$".toRegex(RegexOption.IGNORE_CASE).matchEntire(inputString) == null) {
            println("The input has invalid characters.")
        } else println("The input has duplicate letters.")
    } else println("The input is a valid string.")
}
