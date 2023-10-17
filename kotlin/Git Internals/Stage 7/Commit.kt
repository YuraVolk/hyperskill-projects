package gitinternals

data class Commit(var committer: String = "", var author: String = "", val parents: MutableList<String> = mutableListOf(), var tree: String = "", var commitMessage: String = "") {
    override fun toString(): String {
        return "tree: $tree\n${if (parents.size > 0) "parents: " else ""}${parents.joinToString(separator = " | ")}" +
                "${if (parents.size > 0) "\n" else ""}author: $author\ncommitter: $committer\ncommit message:\n${commitMessage.trim()}"
    }
}
