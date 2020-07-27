package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BracketsParserTest {
    @Test
    fun parseLeftSquareBracket() {
        testEquivalence('[', LexemeType.LEFT_SQUARE_BRACKET)
    }

    @Test
    fun parseRightSquareBracket() {
        testEquivalence(']', LexemeType.RIGHT_SQUARE_BRACKET)
    }

    @Test
    fun parseLeftRoundBracket() {
        testEquivalence('(', LexemeType.LEFT_ROUND_BRACKET)
    }

    @Test
    fun parseRightRoundBracket() {
        testEquivalence(')', LexemeType.RIGHT_ROUND_BRACKET)
    }

    @Test
    fun unexpectedCharacter() {
        testEquivalence('@', null)
    }

    private fun testEquivalence(ch: Char, lexemeType: LexemeType?) {
        val queryState = getQueryStateMock(ch)
        val bracketsParser = BracketsParser()
        val lexemeParsingResult = bracketsParser.parseLexeme(queryState)
        lexemeType?.let {
            assertNotNull(lexemeParsingResult)
            lexemeParsingResult?.let {
                assertEquals(lexemeType, it.lexeme.lexemeType)
            }
        } ?: assertNull(lexemeParsingResult)
    }

    private fun getQueryStateMock(ch: Char) =
        mock<QueryState> {
            on { currentChar } doReturn ch
            on { it.pointer } doReturn Pointer(1, 1)
            on { nextCharState() } doReturn mock()
        }
}