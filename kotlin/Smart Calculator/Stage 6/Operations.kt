package calculator

enum class Operations(val sign: Char) {
    PLUS('+'),
    MINUS('-');

    override fun toString() = sign.toString()
}