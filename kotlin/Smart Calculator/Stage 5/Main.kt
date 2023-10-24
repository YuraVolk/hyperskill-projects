package calculator

fun main() {
    val calculator = Calculator()
    while (true) {
        val input = readln().trim()
        try {
            when (true) {
                (input == "/exit") -> break
                (input == "/help") -> println("The program takes a valid infix notation with addition and subtraction operations supported.")
                input.isEmpty() -> continue
                (input.startsWith("/")) -> println("Unknown command")
                else -> println(calculator.parseExpression(input))
            }
        } catch (_: Exception) { println("Invalid expression") }
    }

    println("Bye!")
}
