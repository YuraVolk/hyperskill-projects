package processor;

data class Matrix(val matrix: Array<IntArray>, val rows: Int = matrix.size, val cols: Int = matrix[0].size) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!matrix.contentDeepEquals(other.matrix)) return false
        if (rows != other.rows) return false
        if (cols != other.cols) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }

    override fun toString(): String {
        return matrix.joinToString("\n") { it.joinToString(" ") }
    }
}
