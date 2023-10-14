package search

import java.util.*

fun main() {
    println("Enter the number of people:")
    val list = MutableList(readln().toInt()) {
        if (it == 0) println("Enter all people:")
        readln()
    }

    println("\nEnter the number of search queries:")
    val queries = readln().toInt()

    for (i in 1..queries) {
        println("\nEnter data to search people:")
        val item = readln().lowercase()
        val foundPersons = list.filter { it.lowercase().contains(item) }.toTypedArray()
        if (foundPersons.isNotEmpty()) {
            println("\nPeople found:\n${foundPersons.joinToString(separator = "\n")}")
        } else println("No matching people found.")
    }
}
