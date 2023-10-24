package calculator

fun main() {
    while (true) {
        val input = readln().trim()
        when (true) {
            (input == "/exit") -> break
            (input == "/help") -> println("The program calculates the sum of numbers")
            input.isEmpty() -> continue
            else -> println(input.split(" ").sumOf(String::toInt))
        }
    }
    println("Bye!")
}
