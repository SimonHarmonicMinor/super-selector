package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.Pointer
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

    @Test
    fun parseLogicalOperators() {
        var parser = LexemeMachine.of("!  &&  ||")

        val deny = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val and = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val or = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.DENY, deny.lexemeType)
        assertEquals(LexemeType.AND, and.lexemeType)
        assertEquals(LexemeType.OR, or.lexemeType)
    }

    @Test
    fun throwsExceptionIfAndOperatorIsIncomplete() {
        val parser = LexemeMachine.of("  \n \n     & ")
        try {
            parser.peekNextLexeme()
            assert(false)
        } catch (e: LexemeParsingException) {
            assertEquals(3, e.pointer.line)
            assertEquals(7, e.pointer.column)
        }
    }

    @Test
    fun throwsExceptionIfOrOperatorIsIncomplete() {
        val parser = LexemeMachine.of("  \n |")
        try {
            parser.peekNextLexeme()
            assert(false)
        } catch (e: LexemeParsingException) {
            assertEquals(2, e.pointer.line)
            assertEquals(3, e.pointer.column)
        }
    }

    @Test
    fun parseComparingOperators() {
        var parser = LexemeMachine.of(" <   <=  \n\t =   != > >=")

        val lt = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val le = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val eq = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val notEq = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val gt = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val ge = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.LT, lt.lexemeType)
        assertEquals(LexemeType.LE, le.lexemeType)
        assertEquals(LexemeType.EQ, eq.lexemeType)
        assertEquals(LexemeType.NOT_EQ, notEq.lexemeType)
        assertEquals(LexemeType.GT, gt.lexemeType)
        assertEquals(LexemeType.GE, ge.lexemeType)
    }

    @Test
    fun parseSimpleStrings() {
        val firstString = "first\"String"
        val secondString = "second'String"
        var parser = LexemeMachine.of(" '$firstString' \"$secondString\" ")

        val firstStringLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val secondStringLexeme = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.STRING, firstStringLexeme.lexemeType)
        assertEquals(firstString, firstStringLexeme.value)

        assertEquals(LexemeType.STRING, secondStringLexeme.lexemeType)
        assertEquals(secondString, secondStringLexeme.value)
    }

    @Test
    fun throwsExceptionIfSingleQuoteStringHasNotBeenClosed() {
        val parser = LexemeMachine.of("   'str  ")

        try {
            parser.peekNextLexeme()
            assert(false)
        }
        catch (e: LexemeParsingException) {
            assertEquals(1, e.pointer.line)
            assertEquals(10, e.pointer.column)
        }
    }

    @Test
    fun throwsExceptionIfDoubleQuoteStringHasNotBeenClosed() {
        val parser = LexemeMachine.of("   \"str  ")

        try {
            parser.peekNextLexeme()
            assert(false)
        }
        catch (e: LexemeParsingException) {
            assertEquals(1, e.pointer.line)
            assertEquals(10, e.pointer.column)
        }
    }
}