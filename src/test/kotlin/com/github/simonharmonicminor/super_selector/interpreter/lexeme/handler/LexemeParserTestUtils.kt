package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull

object LexemeParserTestUtils {

    fun testEquivalence(lexemeParser: LexemeParser, lexemeType: LexemeType, vararg chars: Char) {
        testCharEqualsToLexemeType(lexemeParser, lexemeType, *chars)
    }

    fun testNullLexemeParsingResult(lexemeParser: LexemeParser, vararg chars: Char) {
        testCharEqualsToLexemeType(lexemeParser, null, *chars)
    }

    private fun testCharEqualsToLexemeType(lexemeParser: LexemeParser, lexemeType: LexemeType?, vararg ch: Char) {
        val queryState = getQueryStateMock(ch, 0)
        val lexemeParsingResult = lexemeParser.parseLexeme(queryState)
        lexemeType?.let {
            Assertions.assertNotNull(lexemeParsingResult)
            lexemeParsingResult?.let {
                Assertions.assertEquals(lexemeType, it.lexeme.lexemeType)
            }
        } ?: assertNull(lexemeParsingResult)
    }

    private fun getQueryStateMock(chars: CharArray, index: Int): QueryState {
        if (index >= chars.size)
            return mock()
        val queryState = mock<QueryState>()
        whenever(queryState.pointer)
            .thenReturn(Pointer(1, 1))
        whenever(queryState.currentChar)
            .thenReturn(chars[index])

        val nextState = getQueryStateMock(chars, index + 1)
        whenever(queryState.nextCharState())
            .thenReturn(nextState)
        return queryState
    }
}