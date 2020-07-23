package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class BracketsParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {
    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        return when (queryState.currentChar) {
            '(' -> LexemeType.LEFT_ROUND_BRACKET
            ')' -> LexemeType.RIGHT_ROUND_BRACKET
            '[' -> LexemeType.LEFT_SQUARE_BRACKET
            ']' -> LexemeType.RIGHT_SQUARE_BRACKET
            else -> null
        }?.let {
            LexemeParsingResult(Lexeme.of(it, queryState.pointer), queryState.nextCharState())
        }
    }
}