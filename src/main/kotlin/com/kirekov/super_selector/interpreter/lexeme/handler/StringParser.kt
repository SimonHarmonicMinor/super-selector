package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.lexeme.Lexeme
import com.kirekov.super_selector.interpreter.lexeme.LexemeParsingResult
import com.kirekov.super_selector.interpreter.lexeme.LexemeType
import com.kirekov.super_selector.interpreter.lexeme.QueryState

class StringParser : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val strStartCh = queryState.currentChar
        if (strStartCh != '\'' && strStartCh != '"')
            return null
        val (str, nextState) = collectCharsWhileConditionTrue(queryState.nextCharState()) { _, ch ->
            ch != strStartCh
        }
        if (nextState.currentChar != strStartCh)
            throw LexemeParsingException(nextState, "Expected $strStartCh character to enclose the string literal")
        return LexemeParsingResult(
                lexeme = Lexeme.of(LexemeType.STRING, queryState.pointer, str),
                nextState = nextState.nextCharState()
        )
    }
}