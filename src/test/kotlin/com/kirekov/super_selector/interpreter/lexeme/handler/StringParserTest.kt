package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class StringParserTest {

    @Test
    fun parseSingleQuoteString() {
        val lexemeParsingResult = testEquivalence(StringParser(), LexemeType.STRING, *"'rave!!'".toCharArray())
        assertEquals("rave!!", lexemeParsingResult!!.lexeme.value)
    }

    @Test
    fun parseDoubleQuoteString() {
        val lexemeParsingResult = testEquivalence(StringParser(), LexemeType.STRING, *"\"hello there\"".toCharArray())
        assertEquals("hello there", lexemeParsingResult!!.lexeme.value)
    }

    @Test
    fun throwsExceptionIfSingleQuoteStringHasNotBeenEnclosed() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(StringParser(), LexemeType.STRING, *"'gonna do some bad stuff".toCharArray())
        }
    }

    @Test
    fun throwsExceptionIfDoubleQuoteStringHasNotBeenEnclosed() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(StringParser(), LexemeType.STRING, *"\"here we go again".toCharArray())
        }
    }

    @Test
    fun throwsExceptionIfSingleQuoteStringHasBeenEnclosedWithDoubleQuote() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(StringParser(), LexemeType.STRING, *"\'oh no please\"".toCharArray())
        }
    }

    @Test
    fun throwsExceptionIfDoubleQuoteStringHasBeenEnclosedWithSingleQuote() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(StringParser(), LexemeType.STRING, *"\"aaaaaaaaa'".toCharArray())
        }
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(StringParser(), '$')
    }
}