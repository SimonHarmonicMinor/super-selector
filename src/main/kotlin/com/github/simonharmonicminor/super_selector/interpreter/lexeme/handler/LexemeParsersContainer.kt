package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class LexemeParsersContainer(private vararg val parsers: LexemeParser) : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        var localQueryState = queryState
        while (IGNORED_SYMBOLS.contains(localQueryState.currentChar)) {
            localQueryState = localQueryState.nextCharState()
        }
        for (parser in parsers) {
            val result = parser.parseLexeme(localQueryState)
            if (result != null)
                return result
        }
        return null
    }

    companion object {
        private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')
    }
}