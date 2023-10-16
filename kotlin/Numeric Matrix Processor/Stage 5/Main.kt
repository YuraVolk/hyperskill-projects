package processor

class MatrixProcessor {
    private enum class MenuOption(val number: Int, private val description: String) {
        ADD_MATRICES(1, "Add matrices"),
        MULTIPLY_BY_CONSTANT(2, "Multiply matrix by a constant"),
        MULTIPLY_MATRICES(3, "Multiply matrices"),
        TRANSPOSE(4, "Transpose matrix"),
        CALCULATE_DETERMINANT(5, "Calculate a determinant"),
        EXIT(0, "Exit");

        fun getDescription() = "$number. $description"
    }

    fun menu() {
        while (true) {
            MenuOption.values().forEach { println(it.getDescription()) }
            print("Your choice: ")
            println(when (readln().toIntOrNull()) {
                MenuOption.ADD_MATRICES.number -> (Matrix.fromInput("first") + Matrix.fromInput("second"))
                MenuOption.MULTIPLY_BY_CONSTANT.number -> (Matrix.fromInput() * print("Enter constant: ").let { readln().toDouble() })
                MenuOption.MULTIPLY_MATRICES.number -> (Matrix.fromInput("first") * Matrix.fromInput("second"))
                MenuOption.TRANSPOSE.number -> {
                    print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\nYour choice: ")
                    when (readln().toInt()) {
                        1 -> Matrix.fromInput().mainLineTranspose()
                        2 -> Matrix.fromInput().sideDiagonalTranspose()
                        3 -> Matrix.fromInput().verticalLineTranspose()
                        4 -> Matrix.fromInput().horizontalLineTranspose()
                        else -> "No such transposition type"
                    }
                }
                MenuOption.CALCULATE_DETERMINANT.number -> Matrix.fromInput().determinant().let { if (it == Double.POSITIVE_INFINITY) Matrix.ERROR_MESSAGE else it }
                MenuOption.EXIT.number -> return
                else -> "No such operation."
            })
            println()
        }
    }
}

fun main() = MatrixProcessor().menu()
