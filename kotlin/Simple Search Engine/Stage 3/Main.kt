package search

import java.util.*

fun main() {
    println("Enter the number of people:")
    val list = MutableList(readln().toInt()) {
        if (it == 0) println("Enter all people:")
        readln()
    }

    var option: Int
    while (true) {
        println("\n" +
                "=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit")
        option = readln().toInt()
        println()
        when (option) {
            1 -> {
                println("Enter a name or email to search all suitable people.")
                val item = readln().lowercase()
                val foundPersons = list.filter { it.lowercase().contains(item) }.toTypedArray()
                if (foundPersons.isNotEmpty()) {
                    println("\nPeople found:\n${foundPersons.joinToString(separator = "\n")}")
                } else println("No matching people found.")
            }
            2 -> println("=== List of people ===\n${list.joinToString(separator = "\n")}")
            0 -> break
            else -> println("Incorrect option! Try again.")
        }
    }
    println("Bye")
}
