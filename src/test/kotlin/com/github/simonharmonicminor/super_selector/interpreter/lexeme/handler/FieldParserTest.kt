package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.exp

internal class FieldParserTest {
    @Test
    fun parseSimpleFieldName() {
        parseFieldName("ahaha")
    }

    @Test
    fun parseFieldNameWithNumbers() {
        parseFieldName("ahaha1235")
    }

    @Test
    fun parseFieldNameWithUnderScore() {
        parseFieldName("you_Man_whats_UP")
    }

    @Test
    fun parseFieldNameWithAdditionalCharacters() {
        parseFieldName("hereIAm555", "hereIAm555&&rock_you_like_a_hurricane")
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(FieldParser(), '%')
    }

    @Test
    fun parseSelectKeyWord() {
        parseKeyWords(LexemeType.SELECT, "select^^$", "SeLecT", "SELECT")
    }

    @Test
    fun parseWhereKeyWord() {
        parseKeyWords(LexemeType.WHERE, "where", "wHerE ***", "WHERE")
    }

    @Test
    fun parseOrderKeyWord() {
        parseKeyWords(LexemeType.ORDER, "order", "OrDER", "ORDER %$£$£")
    }

    @Test
    fun parseByKeyWord() {
        parseKeyWords(LexemeType.BY, "BY", "bY", "by **")
    }

    @Test
    fun parseAscKeyWord() {
        parseKeyWords(LexemeType.ASC, "asC", "ASC", "Asc")
    }

    @Test
    fun parseDescKeyWord() {
        parseKeyWords(LexemeType.DESC, "deSc", "DEsC", "DESC")
    }

    @Test
    fun parseTrueKeyWord() {
        parseKeyWords(LexemeType.TRUE, "tRue", "TRUE", "True")
    }

    @Test
    fun parseFalseKeyWord() {
        parseKeyWords(LexemeType.FALSE, "faLse", "FALSE", "faLSE  (()()")
    }

    @Test
    fun parseIsKeyWord() {
        parseKeyWords(LexemeType.IS, "iS", "Is", "IS")
    }

    @Test
    fun parseInKeyWord() {
        parseKeyWords(LexemeType.IN, "In", "IN", "iN")
    }

    @Test
    fun parseEscapedKeyWords() {
        val fields = listOf("select", "where", "OrDer", "BY", "ASC", "DeSC", "TRuE", "false", "not", "is", "in")
        fields.forEach {
            parseFieldName(it, "`$it`")
        }
    }

    private fun parseFieldName(fieldName: String) {
        parseFieldName(fieldName, fieldName)
    }

    private fun parseFieldName(expected: String, input: String) {
        val lexemeParsingResult = testEquivalence(FieldParser(), LexemeType.FIELD, *input.toCharArray())
        assertEquals(expected, lexemeParsingResult!!.lexeme.value)
    }

    private fun parseKeyWords(lexemeType: LexemeType, vararg inputs: String) {
        inputs.forEach {
            parseKeyWord(lexemeType, it)
        }
    }

    private fun parseKeyWord(expected: LexemeType, input: String) {
        testEquivalence(FieldParser(), expected, *input.toCharArray())
    }
}