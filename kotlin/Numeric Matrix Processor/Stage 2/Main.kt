package processor

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
        } else println(Matrix(Array(matrixA.rows) { i -> IntArray(matrixA.cols) { j -> matrixA.matrix[i][j] + matrixB.matrix[i][j] } }))
    }

    fun multiplyMatrices() {
        val matrix = inputMatrix(); val constant = readln().toInt()
        println(Matrix(Array(matrix.rows) { i -> IntArray(matrix.cols) { j -> matrix.matrix[i][j] * constant } }))
    }
}

fun main() = MatrixProcessor().multiplyMatrices()
