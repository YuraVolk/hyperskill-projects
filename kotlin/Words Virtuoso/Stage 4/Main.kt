package wordsvirtuoso

fun main(args: Array<String>) {
    try {
        Game(args).startGame()
    } catch (e: Exception) { println(e.message) }
}
