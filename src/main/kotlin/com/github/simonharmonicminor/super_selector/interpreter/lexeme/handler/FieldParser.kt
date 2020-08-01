package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState


class FieldParser : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val ch = queryState.currentChar
        if (ch?.isLetter() == true || ch == '_') {
            val (identifier, afterIdentifierState) = collectCharsWhileConditionTrue(queryState) { _, curr ->
                curr.isLetter() || curr.isDigit() || curr == '_'
            }
            val possibleKeyword = KEYWORDS[identifier.toLowerCase()]
            val lexemeType = possibleKeyword ?: LexemeType.FIELD
            return LexemeParsingResult(
                lexeme = Lexeme.of(lexemeType, queryState.pointer, identifier),
                nextState = afterIdentifierState
            )
        }
        if (ch == '`') {
            val (identifier, afterIdentifierState) = collectCharsWhileConditionTrue(queryState.nextCharState()) { _, curr ->
                curr != '`'
            }
            return LexemeParsingResult(
                lexeme = Lexeme.of(LexemeType.FIELD, queryState.pointer, identifier),
                nextState = afterIdentifierState
            )
        }
        return null
    }

    companion object {
        private val KEYWORDS = setOf(
            LexemeType.SELECT,
            LexemeType.WHERE,
            LexemeType.ORDER,
            LexemeType.BY,
            LexemeType.ASC,
            LexemeType.DESC,
            LexemeType.TRUE,
            LexemeType.FALSE,
            LexemeType.NOT,
            LexemeType.IS,
            LexemeType.IN
        ).map { it.placeholder to it }.toMap()
    }
}