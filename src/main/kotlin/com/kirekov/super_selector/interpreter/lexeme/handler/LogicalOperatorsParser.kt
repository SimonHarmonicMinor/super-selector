package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.lexeme.Lexeme
import com.kirekov.super_selector.interpreter.lexeme.LexemeParsingResult
import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.QueryState

class LogicalOperatorsParser : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
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