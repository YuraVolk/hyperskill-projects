package search

fun main() {
    val list = readln().split(" ")
    val item = readln()
    println(if (list.contains(item)) list.indexOf(item) + 1 else "Not found")
}
