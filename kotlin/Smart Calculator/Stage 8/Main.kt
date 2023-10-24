package calculator

fun main() {
    val calculator = Calculator()
    while (true) {
        val input = readln().trim()
        try {
            when (true) {
                (input == "/exit") -> break
                (input == "/help") -> println("The program takes a valid infix notation with variables and 5 most common operations supported.")
                input.isEmpty() -> continue
                (input.startsWith("/")) -> println("Unknown command")
                else -> calculator.parseInput(input)?.let(::println)
            }
        } catch (e: Exception) { println(e.message) }
    }

    println("Bye!")
}
