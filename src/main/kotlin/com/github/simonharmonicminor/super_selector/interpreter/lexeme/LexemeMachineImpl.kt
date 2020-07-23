package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult
import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserHandler

internal class LexemeMachineImpl(
    private val queryState: QueryState,
    private val parserHandlersChain: LexemeParserHandler
) : LexemeMachine {
    private val nextLexemeRetriever: CachedResult<LexemeParsingResult>

    init {
        nextLexemeRetriever = CachedResult {
            getNextLexeme()
        }
    }

    override fun peekNextLexeme() = nextLexemeRetriever().lexeme

    override fun movedToNextLexeme() =
        LexemeMachineImpl(nextLexemeRetriever().nextState, parserHandlersChain)

    private fun getNextLexeme(): LexemeParsingResult {
        if (queryState.currentChar == null)
            throw LexemeParsingException(
                queryState,
                "Reached the end of the query. No more lexemes are available"
            )
        return parserHandlersChain.parseLexeme(queryState)
    }
}