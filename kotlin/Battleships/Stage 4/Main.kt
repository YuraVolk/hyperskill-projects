package battleship

fun main() {
    Board(10).apply {
        printBoard()
        println("\nThe game starts!\n").also { printBoard(fog = true) }.also { println("\nTake a shot!\n") }
        while (aliveShips.isNotEmpty()) takeShot()
    }
}
