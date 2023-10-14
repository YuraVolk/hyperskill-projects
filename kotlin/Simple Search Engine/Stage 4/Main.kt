package search

import java.io.File

class SimpleSearchEngine(private val data: List<String>) {
    init {
        menu()
    }

    private fun menu() {
        while (true) {
            println("\n" +
                    "=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit")
            when (readln()) {
                "1" -> findByInfo()
                "2" -> printData()
                "0" -> break
                else -> println("Incorrect option! Try again.")
            }
        }
        println("Bye!")
    }

    private fun findByInfo() {
        println("Enter a name or email to search all suitable people.")
        val item = readln().lowercase()
        val foundPersons = data.filter { it.lowercase().contains(item) }.toTypedArray()
        if (foundPersons.isNotEmpty()) {
            println("\nPeople found:\n${foundPersons.joinToString(separator = "\n")}")
        } else println("No matching people found.")
    }

    private fun printData() {
        println("=== List of people ===\n${data.joinToString(separator = "\n")}")
    }
}

fun main(args: Array<String>) {
    val lines = mutableListOf<String>()
    File(args[1]).inputStream().bufferedReader().forEachLine { lines.add(it) }
    SimpleSearchEngine(lines.toList())
}
