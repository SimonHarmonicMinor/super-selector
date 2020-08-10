package com.kirekov.super_selector.interpreter.lexeme

import com.kirekov.super_selector.CachedResult
import com.kirekov.super_selector.LexemeParsingException
import com.kirekov.super_selector.interpreter.lexeme.handler.LexemeParser

internal class LexemeMachineImpl(
        private val queryState: QueryState,
        private val lexemeParser: LexemeParser
) : LexemeMachine {
    private val nextLexemeRetriever: CachedResult<LexemeParsingResult> = CachedResult {
        getNextLexeme()
    }

    override fun peekNextLexeme() = nextLexemeRetriever().lexeme

    override fun movedToNextLexeme() =
            LexemeMachineImpl(nextLexemeRetriever().nextState, lexemeParser)

    private fun getNextLexeme(): LexemeParsingResult {
        return lexemeParser.parseLexeme(queryState)
                ?: throw LexemeParsingException(
                        queryState,
                        queryState.currentChar?.let {
                            "Unexpected character: $it"
                        } ?: "Reached the end of the query. No more lexemes are available"
                )
    }
}