package calculator

import java.lang.StringBuilder
import java.util.*
import kotlin.IllegalStateException

class Calculator {
    companion object Errors {
        const val INVALID_EXPRESSION_ERROR = "Invalid expression"
        const val INVALID_VARIABLE_NAME = "Invalid identifier"
        const val INVALID_VARIABLE_VALUE = "Invalid assignment"
        const val NO_VARIABLE = "Unknown variable"
    }

    private val variables = mutableMapOf<String, Int>()

    private fun parseStringToInfix(expression: String): Stack<String> {
        val notation = Stack<String>()
        var index = 0
        while (index < expression.length) {
            val char = expression[index]
            var isDigitCondition = (char == Operations.MINUS.sign || char == Operations.PLUS.sign)
                    && expression.getOrNull(index + 1)?.isLetterOrDigit() == true || char.isLetterOrDigit()
            if (notation.isNotEmpty() && Operations.values().none { it.toString() == notation.peek() }) isDigitCondition = false
            if (isDigitCondition) {
                val resultingNumber = StringBuilder(char.toString())
                while (expression.getOrNull(++index)?.isLetterOrDigit() == true) resultingNumber.append(expression[index])
                check(resultingNumber.drop(1).let { sequence -> sequence.all { it.isDigit() } || sequence.all { it.isLetter() } })
                notation.push(resultingNumber.toString())
            } else  {
                val operands = mutableListOf<Char>()
                while (Operations.values().any {
                    it.sign == expression.getOrNull(index) && it.sign == (operands.firstOrNull() ?: it.sign)
                }) expression.getOrNull(index++)?.let { operands.add(it) }
                check(operands.isNotEmpty())
                if (operands.all { it == Operations.PLUS.sign }) {
                    notation.push(Operations.PLUS.toString())
                } else notation.push(if (operands.size % 2 == 0) Operations.PLUS.toString() else Operations.MINUS.toString())
            }
        }

        return Stack<String>().apply { addAll(notation.reversed()) }
    }

    private fun extractValue(string: String) = string.toIntOrNull() ?: variables.getOrElse(string) { throw IllegalStateException(NO_VARIABLE) }
    private fun parseExpression(expression: String): Int {
        try {
            val infix = parseStringToInfix(expression.filterNot { it.isWhitespace() })
            var result = extractValue(infix.pop())
            while (infix.isNotEmpty()) {
                when (infix.pop()) {
                    Operations.PLUS.toString() -> result += extractValue(infix.pop())
                    Operations.MINUS.toString() -> result -= extractValue(infix.pop())
                    else -> throw IllegalStateException()
                }
            }

            return result
        } catch (e: Exception) { throw IllegalStateException(if (e.message == NO_VARIABLE) NO_VARIABLE else INVALID_EXPRESSION_ERROR) }
    }

    private fun validateVariableName(variable: String) = "[a-z]+".toRegex(RegexOption.IGNORE_CASE).matches(variable)
    private fun parseAssignment(assignment: String): Int? {
        val (leftPart, rightPart) = assignment.split("=").map(String::trim)
        check(validateVariableName(leftPart)) { INVALID_VARIABLE_NAME }
        check(assignment.count { it == '=' } != 2)  { INVALID_VARIABLE_VALUE }
        check(validateVariableName(rightPart) || rightPart.toIntOrNull() != null) { INVALID_VARIABLE_VALUE }
        check(rightPart.toIntOrNull() != null || variables.containsKey(rightPart)) { NO_VARIABLE }
        variables[leftPart] = rightPart.toIntOrNull() ?: variables[rightPart]!!
        return null
    }

    fun parseInput(input: String): Int? = if (input.contains("=")) parseAssignment(input) else parseExpression(input)
}
