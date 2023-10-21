package battleship

class Game {
    companion object {
        private const val SIZE = 10
        private const val ENTER_MESSAGE = "Press Enter and pass the move to another player"
    }

    private val boards = mutableListOf<Board>()

    private fun inputBoard(index: Int) {
        println("Player $index, place your ships on the game field\n")
        boards.add(Board(SIZE).apply { printBoard() }.also { println(ENTER_MESSAGE) }).also { readln() }
    }

    private fun printLine() = (1..SIZE * 2 + 1).forEach { _ -> print("-") }.also { println() }
    private fun takeTurn(firstBoard: Board, secondBoard: Board, index: Int): Boolean {
        secondBoard.printBoard(fog = true).also { printLine() }
        firstBoard.printBoard(fog = false)
        println("\nPlayer $index, it's your turn:\n").also { secondBoard.takeShot() }
        if (secondBoard.aliveShips.isEmpty()) return true
        println(ENTER_MESSAGE).also { readln() }
        return false
    }

    fun startGame() {
        inputBoard(1).also { inputBoard(2) }
        while (boards.all { it.aliveShips.isNotEmpty() }) {
            if (takeTurn(boards.first(), boards.last(), index = 1)) continue
            if (takeTurn(boards.last(), boards.first(), index = 2)) continue
        }
    }
}
