package processor

const val ERROR_MESSAGE = "The operation cannot be performed.";

class MatrixProcessor {
    enum class MenuOption(val number: Int, private val description: String) {
        ADD_MATRICES(1, "Add matrices"),
        MULTIPLY_BY_CONSTANT(2, "Multiply matrix by a constant"),
        MULTIPLY_MATRICES(3, "Multiply matrices"),
        TRANSPOSE(4, "Transpose matrix"),
        EXIT(0, "Exit");

        fun getDescription() = "$number. $description"
    }

    private fun inputMatrix(matrixDescription: String? = null): Matrix {
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

    private fun addMatrices() {
        val matrixA = inputMatrix("first"); val matrixB = inputMatrix("second")
        if (matrixA.rows != matrixB.rows || matrixA.cols != matrixB.cols) {
            println(ERROR_MESSAGE)
        } else println(Matrix(Array(matrixA.rows) { i -> DoubleArray(matrixA.cols) { j -> matrixA.matrix[i][j] + matrixB.matrix[i][j] } }))
    }

    private fun multiplyByConstant() {
        val matrix = inputMatrix()
        print("Enter constant: ")
        val constant = readln().toDouble()
        println(Matrix(Array(matrix.rows) { i -> DoubleArray(matrix.cols) { j -> matrix.matrix[i][j] * constant } }))
    }

    private fun multiplyMatrices() {
        val matrixA = inputMatrix("first"); val matrixB = inputMatrix("second")
        if (matrixA.cols != matrixB.rows) {
            println(ERROR_MESSAGE)
        } else println(Matrix(
                Array(matrixA.rows) { i -> DoubleArray(matrixB.cols)
                { j -> matrixA.matrix[i].indices.sumOf { k -> matrixA.matrix[i][k] * matrixB.matrix[k][j] } } }).toString())
    }

    private fun transposeMatrix() {
        print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\nYour choice: ")
        val choice = readln().toInt(); val matrix = inputMatrix()
        println(when (choice) {
            1 -> Matrix(Array(matrix.cols) { col -> DoubleArray(matrix.rows) { row -> matrix.matrix[row][col] } })
            2 -> Matrix(Array(matrix.cols) { col -> DoubleArray(matrix.rows) { row -> matrix.matrix[matrix.rows - 1 - row][matrix.cols - 1 - col] } })
            3 -> Matrix(Array(matrix.cols) { col -> DoubleArray(matrix.rows) { row -> matrix.matrix[col][matrix.rows - 1 - row] } })
            4 -> Matrix(Array(matrix.cols) { col -> DoubleArray(matrix.rows) { row -> matrix.matrix[matrix.cols - 1 - col][row] } })
            else -> ERROR_MESSAGE
        })
    }

    fun menu() {
        while (true) {
            MenuOption.values().forEach { println(it.getDescription()) }
            print("Your choice: ")
            when (readln().toInt()) {
                MenuOption.ADD_MATRICES.number -> addMatrices()
                MenuOption.MULTIPLY_BY_CONSTANT.number -> multiplyByConstant()
                MenuOption.MULTIPLY_MATRICES.number -> multiplyMatrices()
                MenuOption.TRANSPOSE.number -> transposeMatrix()
                MenuOption.EXIT.number -> return
            }
            println()
        }
    }
}

fun main() = MatrixProcessor().menu()
