package gitinternals

data class FileInfo(val bytes: ByteArray, val type: String, val innerContent: String, val stringContents: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileInfo

        if (!bytes.contentEquals(other.bytes)) return false
        if (type != other.type) return false
        if (innerContent != other.innerContent) return false
        if (stringContents != other.stringContents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes.contentHashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + innerContent.hashCode()
        result = 31 * result + stringContents.hashCode()
        return result
    }
}
