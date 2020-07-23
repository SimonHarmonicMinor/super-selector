package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class NumberParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {
    private val digitsCollectingCondition: (Pointer, Char) -> Boolean = { _, ch -> ch.isDigit() }

    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        if (queryState.currentChar?.isDigit() == true) {
            val (decimalPart, dotQueryState) = collectCharsWhileConditionTrue(queryState, digitsCollectingCondition)
            if (dotQueryState.currentChar != '.') {
                return LexemeParsingResult(
                    lexeme = Lexeme.of(LexemeType.DECIMAL, queryState.pointer, decimalPart.toLong()),
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
                    "${decimalPart}.${fractionalPart}".toDouble()
                ),
                nextState = afterDoubleNumberState
            )
        }
        return null
    }
}