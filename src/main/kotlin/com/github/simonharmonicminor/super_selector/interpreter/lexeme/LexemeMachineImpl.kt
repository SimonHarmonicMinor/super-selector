package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult
import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParsersContainer

internal class LexemeMachineImpl(
    private val queryState: QueryState,
    private val lexemeParsersContainer: LexemeParsersContainer
) : LexemeMachine {
    private val nextLexemeRetriever: CachedResult<LexemeParsingResult>

    init {
        nextLexemeRetriever = CachedResult {
            getNextLexeme()
        }
    }

    override fun peekNextLexeme() = nextLexemeRetriever().lexeme

    override fun movedToNextLexeme() =
        LexemeMachineImpl(nextLexemeRetriever().nextState, lexemeParsersContainer)

    private fun getNextLexeme(): LexemeParsingResult {
        if (queryState.currentChar == null)
            throw LexemeParsingException(
                queryState,
                "Reached the end of the query. No more lexemes are available"
            )
        return lexemeParsersContainer.parseLexeme(queryState)
    }
}