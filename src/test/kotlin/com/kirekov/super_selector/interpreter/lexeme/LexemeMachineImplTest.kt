package com.kirekov.super_selector.interpreter.lexeme

import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.Pointer
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParser
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.getQueryStateMock
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class LexemeMachineImplTest {

    @Test
    fun peekNextLexemeThrowsExceptionIfNoLexemeIsPresent() {
        val queryState = getQueryStateMock()
        val lexemeParser = mock<LexemeParser>()
        val lexemeMachine = LexemeMachineImpl(queryState, lexemeParser)
        assertThrows(LexemeParsingException::class.java) {
            lexemeMachine.peekNextLexeme()
        }
    }

    @Test
    fun peekNextLexemeThrowsExceptionIfStuckIntoUnexpectedCharacter() {
        val queryState = getQueryStateMock('$')
        val lexemeParser = mock<LexemeParser>()
        val lexemeMachine = LexemeMachineImpl(queryState, lexemeParser)
        assertThrows(LexemeParsingException::class.java) {
            lexemeMachine.peekNextLexeme()
        }
    }

    @Test
    fun movedToNextLexemeThrowsExceptionIfNoLexemeIsPresent() {
        val queryState = getQueryStateMock()
        val lexemeParser = mock<LexemeParser>()
        val lexemeMachine = LexemeMachineImpl(queryState, lexemeParser)
        assertThrows(LexemeParsingException::class.java) {
            lexemeMachine.movedToNextLexeme()
        }
    }

    @Test
    fun peekNextLexemeReturnsTheSameResult() {
        val queryState = getQueryStateMock()
        val lexemeParsingResult = getLexemeParsingResult(LexemeType.FALSE)
        val lexemeParser = mock<LexemeParser> {
            on { parseLexeme(queryState) }.thenReturn(lexemeParsingResult)
        }
        val lexemeMachine = LexemeMachineImpl(queryState, lexemeParser)
        val peeked1 = lexemeMachine.peekNextLexeme()
        val peeked2 = lexemeMachine.peekNextLexeme()
        val peeked3 = lexemeMachine.peekNextLexeme()

        assertEquals(lexemeParsingResult.lexeme.lexemeType, peeked1.lexemeType)
        assertEquals(lexemeParsingResult.lexeme.lexemeType, peeked3.lexemeType)
        assertEquals(lexemeParsingResult.lexeme.lexemeType, peeked2.lexemeType)
    }

    @Test
    fun movedToNextLexemeReturnsTheNextState() {
        val queryState = getQueryStateMock(*"abc".toCharArray())
        val lexemeParser = mock<LexemeParser> {
            on { parseLexeme(any()) }.thenAnswer {
                val currState = it.arguments[0] as QueryState
                when (currState.currentChar) {
                    'a' -> getLexemeParsingResult(LexemeType.DECIMAL, currState.nextCharState())
                    'b' -> getLexemeParsingResult(LexemeType.DOUBLE, currState.nextCharState())
                    'c' -> getLexemeParsingResult(LexemeType.FIELD, currState.nextCharState())
                    else -> null
                }
            }
        }

        val lexemeMachine = LexemeMachineImpl(queryState, lexemeParser)
        val lexeme1 = lexemeMachine.peekNextLexeme()
        val lexeme2 = lexemeMachine
                .movedToNextLexeme()
                .peekNextLexeme()
        val lexeme3 = lexemeMachine
                .movedToNextLexeme()
                .movedToNextLexeme()
                .peekNextLexeme()

        assertEquals(LexemeType.DECIMAL, lexeme1.lexemeType)
        assertEquals(LexemeType.DOUBLE, lexeme2.lexemeType)
        assertEquals(LexemeType.FIELD, lexeme3.lexemeType)
    }

    private fun getLexemeParsingResult(lexemeType: LexemeType) =
            getLexemeParsingResult(lexemeType, mock())

    private fun getLexemeParsingResult(lexemeType: LexemeType, nextState: QueryState) =
            LexemeParsingResult(
                    lexeme = Lexeme.of(lexemeType, Pointer(1, 1)),
                    nextState = nextState
            )
}