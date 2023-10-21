package battleship

class Board(private val size: Int) {
    val board = Array(size) { Array(size) { Cell.EMPTY } }
    val aliveShips = mutableListOf<List<Pair<Int, Int>>>()
    private val boardRange = 0 until size

    init {
        BoardInitializer(this).initBoardFromInput()
    }

    fun getAdjacentCoordinates(coordinates: List<List<Int>>): List<Pair<Int, Int>> {
        var (startRow, startCol) = coordinates.first()
        var (endRow, endCol) = coordinates.last()
        val isHorizontal = startRow == endRow
        val adjacentCells = mutableListOf<Pair<Int, Int>>()
        if (endRow <= startRow) startRow = endRow.also { endRow = startRow }
        if (endCol <= startCol) startCol = endCol.also { endCol = startCol }

        if (isHorizontal) {
            for (col in (startCol - 1)..(endCol + 1)) {
                getCoordinateOrNull(startRow - 1, col)?.let { adjacentCells.add(Pair(startRow - 1, col)) }
                getCoordinateOrNull(startRow + 1, col)?.let { adjacentCells.add(Pair(startRow + 1, col)) }
            }
            for (row in (startRow - 1)..(startRow + 1)) {
                getCoordinateOrNull(row, startCol - 1)?.let { adjacentCells.add(Pair(row, startCol - 1)) }
                getCoordinateOrNull(row, endCol + 1)?.let { adjacentCells.add(Pair(row, endCol + 1)) }
            }
        } else {
            for (row in (startRow - 1)..(endRow + 1)) {
                getCoordinateOrNull(row, startCol - 1)?.let { adjacentCells.add(Pair(row, startCol - 1)) }
                getCoordinateOrNull(row, startCol + 1)?.let { adjacentCells.add(Pair(row, startCol + 1)) }
            }
            for (col in (startCol - 1)..(startCol + 1)) {
                getCoordinateOrNull(startRow - 1, col)?.let { adjacentCells.add(Pair(startRow - 1, col)) }
                getCoordinateOrNull(endRow + 1, col)?.let { adjacentCells.add(Pair(endRow + 1, col)) }
            }
        }

        return adjacentCells.toList()
    }

    private fun getCoordinateOrNull(i: Int, j: Int) = if (boardRange.contains(i) && boardRange.contains(j)) board[i][j] else null
    fun obtainCoordinates(input: String) = input.split(" ").map {
        listOf(it[0].minus('A'), it.drop(1).toInt() - 1)
    }.let { list -> if (list.all { boardRange.contains(it.first()) && boardRange.contains(it.last()) }) list else null }
    private fun inputCoordinate(inputString: String): List<Int> {
        var input = inputString
        var result: List<Int>? = null
        while (result == null) {
            result = try {
                listOf(input[0].minus('A'), input.drop(1).toInt() - 1).let { list -> if (list.all { boardRange.contains(it) }) list else null }
            } catch (_: Exception) { null }
            if (result == null) println("\nError! You entered the wrong coordinates! Try again:\n").also { input = readln() }
        }

        return result
    }

    fun printBoard(fog: Boolean = false) {
        print(" ").also { for (i in 1..size) print(" $i") }.also { println() }
        board.forEachIndexed { index, cells -> println("${'A' + index} ${cells.joinToString(" ") { if (fog) it.toStringFogged() else it.toString() }}") }
    }

    fun takeShot() {
        val (row, col) = inputCoordinate(readln()).also { println() }
        when (board[row][col]) {
            Cell.EMPTY, Cell.MISS -> println("\nYou missed. Try again:\n").also { board[row][col] = Cell.MISS }
            Cell.SHIP, Cell.HIT -> {
                board[row][col] = Cell.HIT
                val message = run {
                    var isShipSank = false
                    val shipsIterator = aliveShips.iterator()
                    while (shipsIterator.hasNext()) {
                        val ship = shipsIterator.next()
                        if (ship.all { coordinate -> board[coordinate.first][coordinate.second] == Cell.HIT }) {
                            aliveShips.remove(ship).also { isShipSank = true }
                            break
                        }
                    }

                    when (true) {
                        aliveShips.isEmpty() -> "You sank the last ship. You won. Congratulations!"
                        isShipSank -> "You sank a ship!"
                        else -> "You hit a ship!"
                    }
                }
                println(message)
            }
        }
    }
}
