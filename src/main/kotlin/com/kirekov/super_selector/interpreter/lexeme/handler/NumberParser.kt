package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.Pointer
import com.kirekov.super_selector.interpreter.lexeme.Lexeme
import com.kirekov.super_selector.interpreter.lexeme.LexemeParsingResult
import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.QueryState

class NumberParser : LexemeParser {
    private val digitsCollectingCondition: (Pointer, Char) -> Boolean = { _, ch -> ch.isDigit() }

    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val ch = queryState.currentChar
        if (ch?.isDigit() == true || ch == '-') {
            val (sign, nextState) =
                    if (ch == '-')
                        Pair(-1, queryState.nextCharState())
                    else
                        Pair(1, queryState)
            val (decimalPart, dotQueryState) = collectCharsWhileConditionTrue(nextState, digitsCollectingCondition)
            if (decimalPart.isEmpty()) {
                throw LexemeParsingException(
                        queryState = nextState,
                        message = "Number's decimal part should not be empty"
                )
            }
            if (dotQueryState.currentChar != '.') {
                return LexemeParsingResult(
                        lexeme = Lexeme.of(LexemeType.DECIMAL, queryState.pointer, decimalPart.toLong() * sign),
                        nextState = dotQueryState
                )
            }
            val fractionalPartState = dotQueryState.nextCharState()
            if (fractionalPartState.currentChar?.isDigit() != true)
                throw LexemeParsingException(
                        queryState = fractionalPartState,
                        message = "Expected a number in the fractional part"
                )
            val (fractionalPart, afterDoubleNumberState) =
                    collectCharsWhileConditionTrue(fractionalPartState, digitsCollectingCondition)
            return LexemeParsingResult(
                    lexeme = Lexeme.of(
                            LexemeType.DOUBLE,
                            queryState.pointer,
                            "${decimalPart}.${fractionalPart}".toDouble() * sign
                    ),
                    nextState = afterDoubleNumberState
            )
        }
        return null
    }
}