package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')

abstract class LexemeParserHandler(private val next: LexemeParserHandler?) {

    protected abstract fun innerParseLexeme(queryState: QueryState): LexemeParsingResult?

    fun parseLexeme(queryState: QueryState): LexemeParsingResult {
        var localQueryState = queryState
        while (IGNORED_SYMBOLS.contains(localQueryState.currentChar)) {
            localQueryState = localQueryState.nextCharState()
        }
        val result = innerParseLexeme(localQueryState)
        if (result != null)
            return result
        return next?.parseLexeme(localQueryState)
            ?: throw LexemeParsingException(
                localQueryState,
                "Unexpected end of the query"
            )
    }
}
