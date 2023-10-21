package battleship

class Board(private val size: Int) {
    private val board = Array(size) { Array(size) { Cell.EMPTY }  }
    private val boardRange = 0 until size

    init {
        Ships.values().toMutableList().apply {
            var madeError = false
            while (isNotEmpty()) {
                if (madeError) {
                    println("\n").run { madeError = false }
                } else printBoard().run { println("\nEnter the coordinates of the ${first()}:\n") }
                var coordinates: List<List<Int>>?
                try {
                    coordinates = obtainCoordinates(readln())
                } catch (_: Exception) {
                    println("\nError! Invalid coordinate format! Try again:").run { madeError = true }
                    continue
                }
                if (coordinates == null) {
                    println("\nError! Invalid coordinates! Try again:").run { madeError = true }
                    continue
                }

                val (startRow, startCol) = coordinates.first()
                val (endRow, endCol) = coordinates.last()
                val isHorizontal = startRow == endRow
                if (!isHorizontal && startCol != endCol) {
                    println("\nError! Ships cannot be placed diagonally. Try again:").run { madeError = true }
                    continue
                }

                val occupiedCells = if (isHorizontal) { (startCol..endCol).map { col -> board[startRow][col] } } else {
                    (startRow..endRow).map { row -> board[row][startCol] }
                }
                if (occupiedCells.any { it == Cell.SHIP }) {
                    println("\nError! Another ship is already placed in the provided coordinates. Try again:").run { madeError = true }
                    continue
                } else if (getAdjacentCoordinates(coordinates).any { board[it.first][it.second] == Cell.SHIP }) {
                    println("\nError! Ships cannot touch each other in adjacent squares. Try again:").run { madeError = true }
                    continue
                } else if ((isHorizontal && endCol.coerceAtLeast(startCol) - startCol.coerceAtMost(endCol) + 1 != first().length)
                    || (!isHorizontal && endRow.coerceAtLeast(startRow) - startRow.coerceAtMost(endRow) + 1 != first().length)) {
                    println("\nError! The ship length does not match the actual length. Try again:").run { madeError = true }
                    continue
                }

                if (isHorizontal) { (startCol.coerceAtMost(endCol)..endCol.coerceAtLeast(startCol)).forEach {
                    col -> board[startRow][col] = Cell.SHIP
                } } else { (startRow.coerceAtMost(endRow)..endRow.coerceAtLeast(startRow)).forEach {
                    row -> board[row][startCol] = Cell.SHIP
                } }
                remove(first()).run { println() }
            }
        }
    }

    private fun getAdjacentCoordinates(coordinates: List<List<Int>>): List<Pair<Int, Int>> {
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
    private fun obtainCoordinates(input: String) = input.split(" ").map {
        listOf(it[0].minus('A'), it.drop(1).toInt() - 1)
    }.let { list -> if (list.all { boardRange.contains(it.first()) && boardRange.contains(it.last()) }) list else null }

    fun printBoard() {
        print(" ").run { for (i in 1..size) print(" $i") }.run { println() }
        board.forEachIndexed { index, cells -> println("${'A' + index} ${cells.joinToString(" ")}") }
    }
}
