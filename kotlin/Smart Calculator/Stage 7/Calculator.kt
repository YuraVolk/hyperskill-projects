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
            if (isDigitCondition && notation.isNotEmpty() && notation.peek() != "("
                && Operations.values().none { it.toString() == notation.peek() }) isDigitCondition = false
            if (isDigitCondition) {
                val resultingNumber = StringBuilder(char.toString())
                while (expression.getOrNull(++index)?.isLetterOrDigit() == true) resultingNumber.append(expression[index])
                check(resultingNumber.drop(1).let { sequence -> sequence.all { it.isDigit() } || sequence.all { it.isLetter() } })
                notation.push(resultingNumber.toString())
            } else if (char == '(' || char == ')') {
                notation.push(char.toString())
                index++
            } else {
                val operands = mutableListOf<Char>()
                while (Operations.values().any {
                    it.sign == expression.getOrNull(index) && it.sign == (operands.firstOrNull() ?: it.sign)
                }) expression.getOrNull(index++)?.let { operands.add(it) }
                check(operands.isNotEmpty())
                if (operands.all { it == Operations.PLUS.sign }) {
                    notation.push(Operations.PLUS.toString())
                } else if (operands.all { it == Operations.MINUS.sign }) {
                    notation.push(if (operands.size % 2 == 0) Operations.PLUS.toString() else Operations.MINUS.toString())
                } else if (operands.size == 1) notation.push(operands.first().toString()) else throw Exception()
            }
        }

        return notation
    }

    private fun infixToPostfix(infix: Stack<String>): Stack<String> {
        val postfix = Stack<String>()
        val operatorStack = Stack<String>()
        val precedence = Operations.toMap()

        infix.forEach { token ->
            if (token == "(") {
                operatorStack.push(token)
            } else if (token == ")") {
                var seenOpeningBracket = false
                while (!operatorStack.isEmpty() && operatorStack.peek() != "(") {
                    postfix.push(operatorStack.pop())
                    if (operatorStack.peek() == "(") seenOpeningBracket = true
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == "(") {
                    seenOpeningBracket = true
                    operatorStack.pop()
                }
                check(seenOpeningBracket)
            } else if (Operations.values().any { it.toString() == token }) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != "(" && (precedence[operatorStack.peek()]
                        ?: 0) >= (precedence[token] ?: 0)) {
                    postfix.push(operatorStack.pop())
                }
                operatorStack.push(token)
            } else postfix.push(token)
        }

        while (!operatorStack.isEmpty()) postfix.push(operatorStack.pop())
        check(postfix.none { it == "(" || it == ")" })
        return postfix
    }

    private fun extractValue(string: String) = string.toIntOrNull() ?: variables.getOrElse(string) { throw IllegalStateException(NO_VARIABLE) }
    private fun parseExpression(expression: String): Int {
        try {
            val postfix = parseStringToInfix(expression.filterNot { it.isWhitespace() }).let(::infixToPostfix)
            val operandStack = Stack<Int>()
            postfix.forEach { token ->
                if (Operations.values().any { it.toString() == token }) {
                    val secondOperand = operandStack.pop()
                    val firstOperand = operandStack.pop()
                    operandStack.push(Operations.values().find { it.toString() == token }
                        ?.operation?.let { it(firstOperand, secondOperand) } ?: throw Exception())
                } else operandStack.push(extractValue(token))
            }

            return operandStack.pop()
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
