package processor

import java.text.DecimalFormat

class Matrix(private val matrix: Array<DoubleArray>, private val rows: Int = matrix.size, private val cols: Int = matrix[0].size) {
    companion object {
        const val ERROR_MESSAGE = "The operation cannot be performed."

        fun fromInput(matrixDescription: String? = null): Matrix {
            print(listOfNotNull("Enter size of", matrixDescription, "matrix: ").joinToString(separator = " "))
            val (rows, cols) = readln().split(" ").map { it.toInt() }
            println(listOfNotNull("Enter", matrixDescription, "matrix:").joinToString(separator = " "))
            val matrix = Array(rows) { DoubleArray(cols) }
            for (i in 0 until rows) {
                val row = readln().split(" ").map { it.toDouble() }
                for (j in 0 until cols) matrix[i][j] = row[j]
            }

            return Matrix(matrix, rows, cols)
        }
    }

    fun mainLineTranspose() = Matrix(Array(cols) { col -> DoubleArray(rows) { row -> matrix[row][col] } })
    fun sideDiagonalTranspose() = Matrix(Array(cols) { col -> DoubleArray(rows) { row -> matrix[rows - 1 - row][cols - 1 - col] } })
    fun verticalLineTranspose() = Matrix(Array(cols) { col -> DoubleArray(rows) { row -> matrix[col][rows - 1 - row] } })
    fun horizontalLineTranspose() = Matrix(Array(cols) { col -> DoubleArray(rows) { row -> matrix[cols - 1 - col][row] } })

    fun determinant(): Double {
        return when { (rows != cols) -> Double.POSITIVE_INFINITY; (rows == 1) -> matrix[0][0]; else -> {
            (0 until cols).sumOf { col ->
                val sign = if (col % 2 == 0) 1 else -1
                sign * matrix[0][col] * (Matrix(Array(rows - 1) { i ->
                    matrix.slice(1 until rows).map { it.slice(0 until col) + it.slice(col + 1 until cols) }[i].toDoubleArray()
                }).determinant())
            }
        } }
    }

    operator fun plus(second: Matrix): Any {
        return if (rows != second.rows || cols != second.cols) ERROR_MESSAGE else Matrix(Array(rows) { i -> DoubleArray(cols) { j -> matrix[i][j] + second.matrix[i][j] } })
    }

    operator fun times(value: Double) = Matrix(Array(rows) { i -> DoubleArray(cols) { j -> matrix[i][j] * value } })
    operator fun times(second: Matrix): Any {
        return if (cols != second.rows) {
            ERROR_MESSAGE
        } else Matrix(Array(rows) { i -> DoubleArray(second.cols) { j -> matrix[i].indices.sumOf { k -> matrix[i][k] * second.matrix[k][j] } } })
    }

    override fun toString(): String {
        println("The result is:")
        val decimalFormat = DecimalFormat("#.###")
        decimalFormat.isDecimalSeparatorAlwaysShown = false
        return matrix.joinToString("\n") { doubles -> doubles.joinToString(" ") { decimalFormat.format(it) } }
    }
}
