package wordsvirtuoso

fun main(args: Array<String>) {
    val start = System.currentTimeMillis()
    try {
        val game = Game(args).apply { startGame() }
        if (game.tries == 1) {
            println("Amazing luck! The solution was found at once.")
        } else println("The solution was found after ${game.tries} tries in ${(System.currentTimeMillis() - start) / 1000} seconds.")
    } catch (e: Exception) { println(e.message) }
}
