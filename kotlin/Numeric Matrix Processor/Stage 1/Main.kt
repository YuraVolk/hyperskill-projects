package processor

data class Matrix(val matrix: Array<IntArray>, val rows: Int, val cols: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!matrix.contentDeepEquals(other.matrix)) return false
        if (rows != other.rows) return false
        if (cols != other.cols) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }

    override fun toString(): String {
        return matrix.joinToString("\n") { it.joinToString(" ") }
    }
}

class MatrixProcessor {
    private fun inputMatrix(): Matrix {
        val (rows, cols) = readln().split(" ").map { it.toInt() }
        val matrix = Array(rows) { IntArray(cols) }
        for (i in 0 until rows) {
            val row = readln().split(" ").map { it.toInt() }
            for (j in 0 until cols) matrix[i][j] = row[j]
        }

        return Matrix(matrix, rows, cols)
    }

    fun addMatrices() {
        val matrixA = inputMatrix(); val matrixB = inputMatrix()
        if (matrixA.rows != matrixB.rows || matrixA.cols != matrixB.cols) {
            println("ERROR")
        } else println(Matrix(
            Array(matrixA.rows) { i -> IntArray(matrixA.cols) { j -> matrixA.matrix[i][j] + matrixB.matrix[i][j] } },
            matrixA.rows, matrixA.cols
        ))
    }
}

fun main() = MatrixProcessor().addMatrices()
