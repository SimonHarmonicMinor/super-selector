package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LogicalOperatorsParserTest {
    @Test
    fun parseAndOperator() {
        testEquivalence(LogicalOperatorsParser(), LexemeType.AND, '&', '&')
    }


    @Test
    fun parseOrOperator() {
        testEquivalence(LogicalOperatorsParser(), LexemeType.OR, '|', '|')
    }

    @Test
    fun throwsExceptionIfAndOperatorIsNotComplete() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(
                LogicalOperatorsParser(),
                LexemeType.AND,
                '&'
            )
        }
    }

    @Test
    fun throwsExceptionIfOrOperatorIsNotComplete() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(
                LogicalOperatorsParser(),
                LexemeType.OR,
                '|'
            )
        }
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(LogicalOperatorsParser(), '%')
    }
}