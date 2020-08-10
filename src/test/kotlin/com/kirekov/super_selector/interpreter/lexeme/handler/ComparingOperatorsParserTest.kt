package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Test

internal class ComparingOperatorsParserTest {
    @Test
    fun parseLessThanOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.LT, '<')
    }

    @Test
    fun parseLessThanOrEqualOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.LE, '<', '=')
    }

    @Test
    fun parseGreaterThanOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.GT, '>')
    }

    @Test
    fun parseGreaterThanOrEqualOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.GE, '>', '=')
    }

    @Test
    fun parseEqualOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.EQ, '=')
    }

    @Test
    fun parseNotEqualOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.NOT_EQ, '!', '=')
    }

    @Test
    fun parseDenyOperator() {
        testEquivalence(ComparingOperatorsParser(), LexemeType.DENY, '!')
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(ComparingOperatorsParser(), '*')
    }
}