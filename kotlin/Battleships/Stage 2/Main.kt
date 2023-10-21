package battleship

fun main() {
    Board(10).apply {
        printBoard()
        takeShot()
    }
}
