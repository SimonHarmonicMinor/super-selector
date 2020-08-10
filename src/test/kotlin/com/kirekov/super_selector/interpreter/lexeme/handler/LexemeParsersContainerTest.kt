package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.interpreter.Pointer
import com.kirekov.super_selector.interpreter.lexeme.Lexeme
import com.kirekov.super_selector.interpreter.lexeme.LexemeParsingResult
import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.QueryState
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test

internal class LexemeParsersContainerTest {

    @Test
    fun returnsFirstAppropriateResult() {
        val lexeme = Lexeme.of(LexemeType.DOUBLE, Pointer(1, 1))
        val child1 = getLexemeParserMock(lexeme)
        val child2 = getLexemeParserMock(lexeme)
        val lexemeParser = LexemeParsersContainer(child1, child2)
        testEquivalence(lexemeParser, LexemeType.DOUBLE, '#')
        verify(child1, times(1)).parseLexeme(any())
        verify(child2, times(0)).parseLexeme(any())

        mock<LexemeParser> {
            on { parseLexeme(any()) }.thenAnswer {
                it.arguments[0]
            }
        }
    }

    @Test
    fun skipsIgnoredSymbols() {
        val child = mock<LexemeParser> {
            on { parseLexeme(any()) }.thenAnswer {
                val queryState: QueryState = it.arguments[0] as QueryState
                when (queryState.currentChar) {
                    '!' -> LexemeParsingResult(
                            lexeme = Lexeme.of(LexemeType.DECIMAL, Pointer(1, 1)),
                            nextState = mock()
                    )
                    else -> null
                }
            }
        }
        val lexemeParser = LexemeParsersContainer(child)
        testEquivalence(lexemeParser, LexemeType.DECIMAL, *"  \n \r \t     !  ".toCharArray())
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(LexemeParsersContainer(), *"    ".toCharArray())
    }

    private fun getLexemeParserMock(returnValue: Lexeme?) =
            mock<LexemeParser> {
                on { parseLexeme(any()) }.thenReturn(
                        returnValue?.let {
                            LexemeParsingResult(
                                    lexeme = it,
                                    nextState = mock()
                            )
                        }
                )
            }

}