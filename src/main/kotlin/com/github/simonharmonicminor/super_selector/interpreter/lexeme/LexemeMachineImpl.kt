package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult
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

    private fun getNextLexeme() = lexemeParsersContainer.parseLexeme(queryState)
}