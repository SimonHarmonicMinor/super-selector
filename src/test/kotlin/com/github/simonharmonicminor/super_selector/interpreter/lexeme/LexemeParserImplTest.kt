package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LexemeParserImplTest {

    @Test
    fun parseFieldNames() {
        val parser = LexemeParser.of("one two three3 four")

        val lexeme1 = parser.peekNextLexeme()
        val lexeme2 = parser.movedToNextLexeme()
            .peekNextLexeme()
        val lexeme3 = parser.movedToNextLexeme()
            .movedToNextLexeme()
            .peekNextLexeme()
        val lexeme4 = parser.movedToNextLexeme()
            .movedToNextLexeme()
            .movedToNextLexeme()
            .peekNextLexeme()

        assertEquals(LexemeType.FIELD, lexeme1?.lexemeType)
        assertEquals("one", lexeme1?.value)

        assertEquals(LexemeType.FIELD, lexeme2?.lexemeType)
        assertEquals("two", lexeme2?.value)

        assertEquals(LexemeType.FIELD, lexeme3?.lexemeType)
        assertEquals("three3", lexeme3?.value)

        assertEquals(LexemeType.FIELD, lexeme4?.lexemeType)
        assertEquals("four", lexeme4?.value)
    }

}