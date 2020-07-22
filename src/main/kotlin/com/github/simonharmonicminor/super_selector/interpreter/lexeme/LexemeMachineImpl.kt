package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult
import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserHandler

internal class LexemeMachineImpl : LexemeMachine {
    private val query: String
    private val index: Int
    private val nextLexemeRetriever: CachedResult<LexemeParsingResult>
    private val parserHandlersChain: LexemeParserHandler

    constructor(query: String, parserHandlersChain: LexemeParserHandler) : this(query, 0, parserHandlersChain)

    private constructor(query: String, index: Int, parserHandlersChain: LexemeParserHandler) {
        this.query = query
        this.index = index
        nextLexemeRetriever = CachedResult {
            getNextLexeme()
        }
        this.parserHandlersChain = parserHandlersChain
    }

    override fun peekNextLexeme() = nextLexemeRetriever().lexeme

    override fun movedToNextLexeme() = LexemeMachineImpl(query, nextLexemeRetriever().nextIndex, parserHandlersChain)

    private fun getNextLexeme(): LexemeParsingResult {
        if (index >= query.length)
            throw LexemeParsingException("Reached the end of the query. No more lexemes are available")
        return parserHandlersChain.parseLexeme(query, index)
    }
}