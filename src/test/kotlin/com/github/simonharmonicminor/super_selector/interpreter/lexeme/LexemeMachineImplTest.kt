package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LexemeMachineImplTest {

    @Test
    fun throwsExceptionOnTryToPeekLexemeIfNothingIsPresent() {
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
}