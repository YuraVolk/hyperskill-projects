package jsondatabase.server

class JSONDatabase {
    private var array: MutableList<String?> = MutableList(1000) { null }

    fun setValue(index: Int, data: String?) = if (index - 1 !in array.indices) "ERROR" else "OK".also { array[index - 1] = data }
    operator fun get(index: Int) = if (index - 1 !in array.indices || array[index - 1] == null) "ERROR" else array[index - 1] ?: "ERROR"
    fun delete(index: Int) = if (index - 1 !in array.indices) "ERROR" else "OK".also { array[index - 1] = null }
}
