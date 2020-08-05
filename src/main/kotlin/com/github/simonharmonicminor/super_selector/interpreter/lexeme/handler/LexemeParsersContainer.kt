package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class LexemeParsersContainer(private vararg val parsers: LexemeParser) : LexemeParser {

    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val localQueryState = getSkippedIgnoredSymbolsState(queryState)
        for (parser in parsers) {
            val result = parser.parseLexeme(localQueryState)
            if (result != null)
                return result
        }
        return null
    }

    private tailrec fun getSkippedIgnoredSymbolsState(queryState: QueryState): QueryState {
        if (IGNORED_SYMBOLS.contains(queryState.currentChar)) {
            return getSkippedIgnoredSymbolsState(queryState.nextCharState())
        }
        return queryState
    }

    companion object {
        private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')
    }
}