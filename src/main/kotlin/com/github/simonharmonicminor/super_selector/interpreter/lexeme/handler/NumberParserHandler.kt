package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType

class NumberParserHandler(override val next: LexemeParserHandler? = null) : LexemeParserHandler() {
    private val digitsCollectingCondition: (Int, Char) -> Boolean = { _, ch -> ch.isDigit() }

    override fun innerParseLexeme(query: String, startIndex: Int): LexemeParsingResult? {
        if (query[startIndex].isDigit()) {
            val decimalPart = collectCharsWhileConditionTrue(query, startIndex, digitsCollectingCondition)
            val afterDecimalPartIndex = startIndex + decimalPart.length
            if (afterDecimalPartIndex >= query.length || query[afterDecimalPartIndex] != '.') {
                return LexemeParsingResult(
                    lexeme = Lexeme.of(LexemeType.DECIMAL, decimalPart.toLong()),
                    nextIndex = afterDecimalPartIndex
                )
            }

            val fractionalPartStartIndex = afterDecimalPartIndex + 1
            if (!query.getOrElse(fractionalPartStartIndex) { '$' }.isDigit())
                throw LexemeParsingException("Expected a number in the fractional part at $fractionalPartStartIndex")
            val fractionalPart =
                collectCharsWhileConditionTrue(query, fractionalPartStartIndex, digitsCollectingCondition)
            return LexemeParsingResult(
                lexeme = Lexeme.of(LexemeType.DOUBLE, "${decimalPart}.${fractionalPart}".toDouble()),
                nextIndex = fractionalPartStartIndex + fractionalPart.length
            )
        }
        return null
    }
}