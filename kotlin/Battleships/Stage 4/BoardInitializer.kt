package battleship

class BoardInitializer(private val board: Board) {
    fun initBoardFromInput() {
        Ships.values().toMutableList().apply {
            var madeError = false
            while (isNotEmpty()) {
                if (madeError) {
                    println().also { madeError = false }
                } else board.printBoard().also { println("\nEnter the coordinates of the ${first()}:\n") }
                var coordinates: List<List<Int>>?
                try {
                    coordinates = board.obtainCoordinates(readln())
                } catch (_: Exception) {
                    println("\nError! Invalid coordinate format! Try again:").also { madeError = true }
                    continue
                }
                if (coordinates == null) {
                    println("\nError! Invalid coordinates! Try again:").also { madeError = true }
                    continue
                }

                val (startRow, startCol) = coordinates.first()
                val (endRow, endCol) = coordinates.last()
                val isHorizontal = startRow == endRow
                if (!isHorizontal && startCol != endCol) {
                    println("\nError! Ships cannot be placed diagonally. Try again:").also { madeError = true }
                    continue
                }

                val range = if (isHorizontal) { (startCol.coerceAtMost(endCol)..endCol.coerceAtLeast(startCol)) } else {
                    (startRow.coerceAtMost(endRow)..endRow.coerceAtLeast(startRow))
                }
                val occupiedCoordinates = if (!isHorizontal) range.map { row -> Pair(row, startCol) } else range.map { col -> Pair(startRow, col) }
                if (occupiedCoordinates.any { board.board[it.first][it.second] == Cell.SHIP }) {
                    println("\nError! Another ship is already placed in the provided coordinates. Try again:").also { madeError = true }
                    continue
                } else if (board.getAdjacentCoordinates(coordinates).any { board.board[it.first][it.second] == Cell.SHIP }) {
                    println("\nError! Ships cannot touch each other in adjacent squares. Try again:").also { madeError = true }
                    continue
                } else if ((isHorizontal && endCol.coerceAtLeast(startCol) - startCol.coerceAtMost(endCol) + 1 != first().length)
                    || (!isHorizontal && endRow.coerceAtLeast(startRow) - startRow.coerceAtMost(endRow) + 1 != first().length)) {
                    println("\nError! The ship length does not match the actual length. Try again:").also { madeError = true }
                    continue
                }

                occupiedCoordinates.forEach { board.board[it.first][it.second] = Cell.SHIP }
                remove(first().also { board.aliveShips.add(occupiedCoordinates) }).also { println() }
            }
        }
    }
}