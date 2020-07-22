package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType

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

class FieldParserHandler(override val next: LexemeParserHandler? = null) : LexemeParserHandler() {

    override fun innerParseLexeme(query: String, startIndex: Int): LexemeParsingResult? {
        if (query[startIndex].isLetter()) {
            val identifier = collectCharsWhileConditionTrue(query, startIndex) { _, ch ->
                ch.isLetter() || ch.isDigit()
            }
            val nextIndex = startIndex + identifier.length
            val possibleKeyword = KEYWORDS[identifier.toLowerCase()]
            return possibleKeyword?.let {
                LexemeParsingResult(
                    lexeme = Lexeme.of(it),
                    nextIndex = nextIndex
                )
            } ?: LexemeParsingResult(
                lexeme = Lexeme.of(LexemeType.FIELD, identifier),
                nextIndex = nextIndex
            )
        }
        return null
    }
}