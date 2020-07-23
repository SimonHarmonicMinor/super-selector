package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class StringParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {
    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        val strStartCh = queryState.currentChar
        if (strStartCh != '\'' && strStartCh != '"')
            return null
        val (str, nextState) = collectCharsWhileConditionTrue(queryState.nextCharState()) { _, ch -> ch != strStartCh }
        if (nextState.currentChar != strStartCh)
            throw LexemeParsingException(nextState, "Expected $strStartCh character to enclose the string literal")
        return LexemeParsingResult(
            lexeme = Lexeme.of(LexemeType.STRING, queryState.pointer, str),
            nextState = nextState.nextCharState()
        )
    }
}