package search

import java.io.File

class SimpleSearchEngine(private val data: List<String>) {
    private val invertedIndex: Map<String, List<Int>>;

    init {
        invertedIndex = createInvertedIndex()
        menu()
    }

    private fun createInvertedIndex(): Map<String, List<Int>> {
        val index = mutableMapOf<String, MutableList<Int>>()
        for ((i, string) in data.withIndex()) {
            string.lowercase().split("\\s+".toRegex()).forEach {
                val wordNumbers = index.getOrElse(it) { mutableListOf() }.toMutableList()
                wordNumbers.add(i)
                index[it] = wordNumbers
            }
        }

        return index.mapValues { (_, value) -> value.toList() }.toMap()
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
        println("Select a matching strategy: ALL, ANY, NONE")
        val method = readln()
        println("Enter a name or email to search all suitable people.")
        val query = readln().lowercase().split(" ")

        val foundPersonIndices = when (method) {
            "ALL" -> query.mapNotNull { invertedIndex[it] }.reduceOrNull { acc, list -> acc.intersect(list.toSet()).toList() }
            "ANY" -> query.flatMap { invertedIndex[it] ?: emptyList() }.distinct()
            "NONE" -> data.indices.filterNot { index -> query.any { invertedIndex[it]?.contains(index) == true } }
            else -> emptyList()
        } ?: emptyList()

        val foundPersons = foundPersonIndices.mapNotNull { index -> data.getOrNull(index) }.toTypedArray()
        println(if (foundPersons.isEmpty()) "No matching people found." else "\nPeople found:\n${foundPersons.joinToString(separator = "\n")}")
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
