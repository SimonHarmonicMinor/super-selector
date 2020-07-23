package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

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

class FieldParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {

    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        if (queryState.currentChar?.isLetter() == true) {
            val (identifier, afterIdentifierState) = collectCharsWhileConditionTrue(queryState) { _, ch ->
                ch.isLetter() || ch.isDigit()
            }
            val possibleKeyword = KEYWORDS[identifier.toLowerCase()]
            val lexemeType = possibleKeyword ?: LexemeType.FIELD
            return LexemeParsingResult(
                lexeme = Lexeme.of(lexemeType, queryState.pointer, identifier),
                nextState = afterIdentifierState
            )
        }
        return null
    }
}