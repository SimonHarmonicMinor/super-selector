package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class LogicalOperatorsParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {
    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        val ch = queryState.currentChar
        return if (ch == '&' || ch == '|') {
            val next = queryState.nextCharState()
            if (next.currentChar != ch)
                throw LexemeParsingException(next, "Expected '$ch'")
            val lexemeType = if (ch == '&') LexemeType.AND else LexemeType.OR
            LexemeParsingResult(
                lexeme = Lexeme.of(lexemeType, pointer = queryState.pointer),
                nextState = next.nextCharState()
            )
        } else null
    }
}