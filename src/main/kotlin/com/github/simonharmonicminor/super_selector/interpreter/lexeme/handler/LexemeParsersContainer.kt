package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class LexemeParsersContainer(private vararg val handlers: LexemeParser) {
    fun parseLexeme(queryState: QueryState): LexemeParsingResult {
        var localQueryState = queryState
        while (IGNORED_SYMBOLS.contains(localQueryState.currentChar)) {
            localQueryState = localQueryState.nextCharState()
        }
        for (handler in handlers) {
            val result = handler.parseLexeme(localQueryState)
            if (result != null)
                return result
        }
        val message = localQueryState.currentChar?.let {
            "Unexpected character: $it"
        } ?: "Reached the end of the query. No more lexemes are available"
        throw LexemeParsingException(
            localQueryState,
            message
        )
    }

    companion object {
        private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')
    }
}