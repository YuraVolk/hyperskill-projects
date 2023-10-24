package calculator

fun main() {
    val calculator = Calculator()
    while (true) {
        val input = readln().trim()
        when (true) {
            (input == "/exit") -> break
            (input == "/help") -> println("The program takes a valid infix notation with addition and subtraction operations supported.")
            input.isEmpty() -> continue
            else -> println(calculator.parseExpression(input))
        }
    }
    println("Bye!")
}
