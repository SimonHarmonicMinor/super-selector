package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

        assertThrows<LexemeParsingException> { lexemeMachine.peekNextLexeme() }
    }

    @Test
    fun parseComparingOperators() {
        var lexemeMachine = LexemeMachine.of("> \n   >= \n \n  < <= \n = !! !=")
        val gt = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val ge = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val lt = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val le = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val eq = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val deny1 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val deny2 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val notEq = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.GT, gt.lexemeType)
        assertEquals(Pointer(0, 0), gt.pointer)

        assertEquals(LexemeType.GE, ge.lexemeType)
        assertEquals(Pointer(1, 3), ge.pointer)

        assertEquals(LexemeType.LT, lt.lexemeType)
        assertEquals(Pointer(3, 2), lt.pointer)

        assertEquals(LexemeType.LE, le.lexemeType)
        assertEquals(Pointer(3, 4), le.pointer)

        assertEquals(LexemeType.EQ, eq.lexemeType)
        assertEquals(Pointer(4, 1), eq.pointer)

        assertEquals(LexemeType.DENY, deny1.lexemeType)
        assertEquals(Pointer(4, 3), deny1.pointer)

        assertEquals(LexemeType.DENY, deny2.lexemeType)
        assertEquals(Pointer(4, 4), deny2.pointer)

        assertEquals(LexemeType.NOT_EQ, notEq.lexemeType)
        assertEquals(Pointer(4, 6), notEq.pointer)
    }
}