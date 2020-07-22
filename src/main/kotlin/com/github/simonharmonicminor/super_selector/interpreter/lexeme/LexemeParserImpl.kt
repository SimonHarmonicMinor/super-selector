package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult

internal class LexemeParserImpl : LexemeParser {
    private val query: String
    private val index: Int
    private val nextLexemeRetriever: CachedResult<Pair<Lexeme?, Int>>

    constructor(query: String) : this(query, 0)

    private constructor(query: String, index: Int) {
        this.query = query
        this.index = index
        nextLexemeRetriever = CachedResult {
            getNextLexeme()
        }
    }

    override fun peekNextLexeme(): Lexeme? {
        return nextLexemeRetriever().first
    }

    override fun movedToNextLexeme(): LexemeParser {
        val (_, newIndex) = nextLexemeRetriever()
        return LexemeParserImpl(query, newIndex)
    }

    private fun getNextLexeme(): Pair<Lexeme?, Int> {
        if (index >= query.length)
            return Pair(null, index)
        return Pair(null, index)
    }
 }