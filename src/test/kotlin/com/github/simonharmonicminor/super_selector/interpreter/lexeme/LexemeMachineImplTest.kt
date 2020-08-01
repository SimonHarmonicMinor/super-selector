package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

internal class LexemeMachineImplTest {
    @Test
    fun throwsLexemeParsingExceptionOnTryToPeekLexemeIfNothingIsPresent() {
        val parser = LexemeMachine.of("")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun parseDecimalNumbers() {
        var parser = LexemeMachine.of("10 231 -199   \n 445")

        val lexeme1 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme2 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme3 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme4 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.DECIMAL, lexeme1.lexemeType)
        assertEquals(10L, lexeme1.value)

        assertEquals(LexemeType.DECIMAL, lexeme2.lexemeType)
        assertEquals(231L, lexeme2.value)

        assertEquals(LexemeType.DECIMAL, lexeme3.lexemeType)
        assertEquals(-199L, lexeme3.value)

        assertEquals(LexemeType.DECIMAL, lexeme4.lexemeType)
        assertEquals(445L, lexeme4.value)
    }

    @Test
    fun parseDoubleNumbers() {
        var parser = LexemeMachine.of("10.1 56.2 -78.004")

        val lexeme1 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme2 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }
        val lexeme3 = parser.peekNextLexeme().also { parser = parser.movedToNextLexeme() }

        assertEquals(LexemeType.DOUBLE, lexeme1.lexemeType)
        assertEquals(10.1, lexeme1.value)

        assertEquals(LexemeType.DOUBLE, lexeme2.lexemeType)
        assertEquals(56.2, lexeme2.value)

        assertEquals(LexemeType.DOUBLE, lexeme3.lexemeType)
        assertEquals(-78.004, lexeme3.value)
    }

    @Test
    fun throwsLexemeParsingExceptionIfFractionalPartAfterDotIsMissing() {
        val parser = LexemeMachine.of("   \n\t 10013.  ")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun throwsExceptionIfStuckIntoUnexpectedCharacter() {
        val parser = LexemeMachine.of(" > < = $ ")
        assertThrows(LexemeParsingException::class.java) {
            parser.movedToNextLexeme()
                .movedToNextLexeme()
                .movedToNextLexeme()
                .peekNextLexeme()
        }
    }
}