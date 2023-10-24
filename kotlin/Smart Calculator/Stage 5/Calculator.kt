package calculator

import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.util.*

class Calculator {
    private fun parseStringToInfix(expression: String): Stack<String> {
        val notation = Stack<String>()
        var index = 0
        while (index < expression.length) {
            val char = expression[index]
            var isDigitCondition = (char == Operations.MINUS.sign || char == Operations.PLUS.sign) && expression.getOrNull(index + 1)?.isDigit() == true || char.isDigit()
            if (notation.isNotEmpty() && Operations.values().none { it.toString() == notation.peek() }) isDigitCondition = false
            if (isDigitCondition) {
                val resultingNumber = StringBuilder(char.toString())
                while (expression.getOrNull(++index)?.isDigit() == true) resultingNumber.append(expression[index])
                notation.push(resultingNumber.toString())
            } else {
                val operands = mutableListOf<Char>()
                while (Operations.values().any {
                    it.sign == expression.getOrNull(index) && it.sign == (operands.firstOrNull() ?: it.sign)
                }) expression.getOrNull(index++)?.let { operands.add(it) }
                check(operands.isNotEmpty()) { "Illegal characters in input string detected" }
                if (operands.all { it == Operations.PLUS.sign }) {
                    notation.push(Operations.PLUS.toString())
                } else notation.push(if (operands.size % 2 == 0) Operations.PLUS.toString() else Operations.MINUS.toString())
            }
        }

        return Stack<String>().apply { addAll(notation.reversed()) }
    }

    fun parseExpression(expression: String): Int {
        val infix = parseStringToInfix(expression.filterNot { it.isWhitespace() })
        var result = infix.pop().toInt()
        while (infix.isNotEmpty()) {
            when (infix.pop()) {
                Operations.PLUS.toString() -> result += infix.pop().toInt()
                Operations.MINUS.toString() -> result -= infix.pop().toInt()
                else -> throw IllegalStateException("Illegal operator")
            }
        }

        return result
    }
}
