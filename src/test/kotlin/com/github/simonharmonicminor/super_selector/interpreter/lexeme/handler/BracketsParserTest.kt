package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Test

internal class BracketsParserTest {
    @Test
    fun parseLeftSquareBracket() {
        testEquivalence(BracketsParser(), LexemeType.LEFT_SQUARE_BRACKET, '[')
    }

    @Test
    fun parseRightSquareBracket() {
        testEquivalence(BracketsParser(), LexemeType.RIGHT_SQUARE_BRACKET, ']')
    }

    @Test
    fun parseLeftRoundBracket() {
        testEquivalence(BracketsParser(), LexemeType.LEFT_ROUND_BRACKET, '(')
    }

    @Test
    fun parseRightRoundBracket() {
        testEquivalence(BracketsParser(), LexemeType.RIGHT_ROUND_BRACKET, ')')
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(BracketsParser(), '@')
    }
}