package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class NumberParserTest {

    @Test
    fun parsePositiveDecimalNumber() {
        parseDecimalNumber(123, "123**")
    }

    @Test
    fun parseHugePositiveDecimalNumber() {
        parseDecimalNumber(9223372036854775807L, "9223372036854775807  ")
    }

    @Test
    fun parseNegativeDecimalNumber() {
        parseDecimalNumber(-45, "-45")
    }

    @Test
    fun parseHugeNegativeDecimalNumber() {
        parseDecimalNumber(-922337203685477580, "-922337203685477580 ")
    }

    @Test
    fun parseZero() {
        parseDecimalNumber(0, "0")
    }

    @Test
    fun parsePositiveDoubleNumber() {
        parseDoubleNumber(0.3, "0.3")
    }

    @Test
    fun parseHugePositiveDoubleNumber() {
        parseDoubleNumber(123456789.12345, "123456789.12345")
    }

    @Test
    fun parseNegativeDoubleNumber() {
        parseDoubleNumber(-34.567, "-34.567")
    }

    @Test
    fun parseHugeNegativeDoubleNumber() {
        parseDoubleNumber(-123456789.12345678, "-123456789.12345678")
    }

    @Test
    fun throwsExceptionIfFractionalPartAfterDotIsMissing() {
        assertThrows(LexemeParsingException::class.java) {
            parseDoubleNumber(-45.45, "-45.")
        }
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(NumberParser(), 'G')
    }

    private fun parseDecimalNumber(expected: Long, input: String) {
        parseNumber(expected, LexemeType.DECIMAL, input)
    }

    private fun parseDoubleNumber(expected: Double, input: String) {
        parseNumber(expected, LexemeType.DOUBLE, input)
    }

    private fun parseNumber(expected: Number, expectedLexemeType: LexemeType, input: String) {
        val lexemeParsingResult = testEquivalence(NumberParser(), expectedLexemeType, *input.toCharArray())
        assertEquals(expected, lexemeParsingResult!!.lexeme.value)
    }
}