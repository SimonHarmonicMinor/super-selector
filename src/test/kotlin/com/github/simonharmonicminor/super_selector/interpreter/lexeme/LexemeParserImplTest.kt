package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LexemeParserImplTest {

    @Test
    fun peekNextLexeme() {
        val parser = LexemeParser.of("asdasd")
        assertNull(parser.peekNextLexeme())
    }

    @Test
    fun movedToNextLexeme() {
        val parser = LexemeParser.of("dasdasd")
        assertNull(
            parser.movedToNextLexeme()
                .movedToNextLexeme()
                .movedToNextLexeme()
                .peekNextLexeme()
        )
    }
}