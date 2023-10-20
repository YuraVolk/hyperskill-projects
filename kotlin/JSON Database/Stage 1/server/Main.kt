package jsondatabase.server

class Database {
    private var array: MutableList<String?> = MutableList(100) { null }

    operator fun set(index: Int, data: String?) = if (index > 100) println("ERROR") else println("OK").also { array[index - 1] = data }
    operator fun get(index: Int) = if (index < 0 || index > 100 || array[index - 1] == null) "ERROR" else array[index - 1]!!
    fun delete(index: Int) =  if (index < 0 || index > 100) println("ERROR") else println("OK").also { array[index - 1] = null }
}

fun main() {
    val database = Database()
    while (true) {
        val request = readln().split(" ".toRegex(), limit = 3).toTypedArray()
        when (request[0]) {
            "get" -> println(database[request[1].toInt(10)])
            "set" -> database[request[1].toInt(10)] = request[2].replace("~".toRegex(), " ")
            "delete" -> database.delete(request[1].toInt(10))
            "exit" -> break
        }
    }
}
