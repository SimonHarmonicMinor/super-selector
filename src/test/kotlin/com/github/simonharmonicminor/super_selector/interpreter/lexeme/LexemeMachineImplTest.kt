package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LexemeMachineImplTest {

    @Test
    fun throwsLexemeParsingExceptionOnTryToPeekLexemeIfNothingIsPresent() {
        val parser = LexemeMachine.of("")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun parseFieldNames() {
        var parser = LexemeMachine.of("one two three3 four")

        val lexeme1 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme2 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme3 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme4 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.FIELD, lexeme1.lexemeType)
        assertEquals("one", lexeme1.value)

        assertEquals(LexemeType.FIELD, lexeme2.lexemeType)
        assertEquals("two", lexeme2.value)

        assertEquals(LexemeType.FIELD, lexeme3.lexemeType)
        assertEquals("three3", lexeme3.value)

        assertEquals(LexemeType.FIELD, lexeme4.lexemeType)
        assertEquals("four", lexeme4.value)

        assertThrows(LexemeParsingException::class.java) { parser.movedToNextLexeme() }
    }

    @Test
    fun parseKeyWords() {
        var parser = LexemeMachine.of("select \t where \n\r   order by asc  desc true false  not in is")

        val selectLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val whereLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val orderLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val byLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val ascLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val descLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val trueLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val falseLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val notLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val inLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val isLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.SELECT, selectLexeme.lexemeType)
        assertEquals(LexemeType.WHERE, whereLexeme.lexemeType)
        assertEquals(LexemeType.ORDER, orderLexeme.lexemeType)
        assertEquals(LexemeType.BY, byLexeme.lexemeType)
        assertEquals(LexemeType.ASC, ascLexeme.lexemeType)
        assertEquals(LexemeType.DESC, descLexeme.lexemeType)
        assertEquals(LexemeType.TRUE, trueLexeme.lexemeType)
        assertEquals(LexemeType.FALSE, falseLexeme.lexemeType)
        assertEquals(LexemeType.NOT, notLexeme.lexemeType)
        assertEquals(LexemeType.IN, inLexeme.lexemeType)
        assertEquals(LexemeType.IS, isLexeme.lexemeType)
    }

    @Test
    fun parseDecimalNumbers() {
        var parser = LexemeMachine.of("10 231 199   \n 445")

        val lexeme1 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme2 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme3 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme4 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.DECIMAL, lexeme1.lexemeType)
        assertEquals(10L, lexeme1.value)

        assertEquals(LexemeType.DECIMAL, lexeme2.lexemeType)
        assertEquals(231L, lexeme2.value)

        assertEquals(LexemeType.DECIMAL, lexeme3.lexemeType)
        assertEquals(199L, lexeme3.value)

        assertEquals(LexemeType.DECIMAL, lexeme4.lexemeType)
        assertEquals(445L, lexeme4.value)
    }

    @Test
    fun parseDoubleNumbers() {
        var parser = LexemeMachine.of("10.1 56.2 78.004")

        val lexeme1 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme2 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme3 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.DOUBLE, lexeme1.lexemeType)
        assertEquals(10.1, lexeme1.value)

        assertEquals(LexemeType.DOUBLE, lexeme2.lexemeType)
        assertEquals(56.2, lexeme2.value)

        assertEquals(LexemeType.DOUBLE, lexeme3.lexemeType)
        assertEquals(78.004, lexeme3.value)
    }

    @Test
    fun throwsLexemeParsingExceptionIfFractionalPartAfterDotIsMissing() {
        val parser = LexemeMachine.of("   \n\t 10013.  ")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun parseBrackets() {
        var parser = LexemeMachine.of(" \n\t\r  [ ]  (  ) ")

        val leftSquareBracket = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val rightSquareBracket = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val leftRoundBracket = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val rightRoundBracket = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.LEFT_SQUARE_BRACKET, leftSquareBracket.lexemeType)
        assertEquals(LexemeType.RIGHT_SQUARE_BRACKET, rightSquareBracket.lexemeType)
        assertEquals(LexemeType.LEFT_ROUND_BRACKET, leftRoundBracket.lexemeType)
        assertEquals(LexemeType.RIGHT_ROUND_BRACKET, rightRoundBracket.lexemeType)
    }
}