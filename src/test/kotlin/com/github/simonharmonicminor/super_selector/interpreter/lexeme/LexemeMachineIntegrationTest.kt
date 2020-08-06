package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexemeMachineIntegrationTest {
    @Test
    fun parseBrackets() {
        var lexemeMachine = LexemeMachine.of("( )  \n\r \t ] [")
        val leftRoundBracket = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val rightRoundBracket = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val rightSquareBracket = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val leftSquareBracket = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.LEFT_ROUND_BRACKET, leftRoundBracket.lexemeType)
        assertEquals(Pointer(0, 0), leftRoundBracket.pointer)

        assertEquals(LexemeType.RIGHT_ROUND_BRACKET, rightRoundBracket.lexemeType)
        assertEquals(Pointer(0, 2), rightRoundBracket.pointer)

        assertEquals(LexemeType.RIGHT_SQUARE_BRACKET, rightSquareBracket.lexemeType)
        assertEquals(Pointer(1, 4), rightSquareBracket.pointer)

        assertEquals(LexemeType.LEFT_SQUARE_BRACKET, leftSquareBracket.lexemeType)
        assertEquals(Pointer(1, 6), leftSquareBracket.pointer)
    }
}