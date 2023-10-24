package calculator

import kotlin.math.pow

enum class Operations(val sign: Char, val priority: Int, val operation: (a: Int, b: Int) -> Int) {
    PLUS('+', 0, { a, b -> a + b }),
    MINUS('-', 0, { a, b -> a - b }),
    MULTIPLY('*', 1, { a, b -> a * b }),
    DIVIDE('/', 1, { a, b -> a / b }),
    POWER('^', 2, { a, b -> a.toDouble().pow(b.toDouble()).toInt() });

    companion object {
        fun toMap(): Map<String, Int> = values().associate { it.toString() to it.priority }
    }

    override fun toString() = sign.toString()
}
