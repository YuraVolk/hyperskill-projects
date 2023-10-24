package calculator

fun main() {
    while (true) {
        val input = readln().trim()
        if (input == "/exit") break
        if (input.isEmpty()) continue
        println(input.split(" ").sumOf(String::toInt))
    }
    println("Bye!")
}
