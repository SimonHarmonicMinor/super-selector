package com.github.simonharmonicminor.super_selector.interpreter.lexeme

internal class LexemeParserImpl : LexemeParser {
    private val query: String
    private val index: Int

    constructor(query: String) : this(query, 0)

    private constructor(query: String, index: Int) {
        this.query = query
        this.index = index
    }

    override fun peekNextLexeme(): Lexeme? {
        TODO("Not yet implemented")
    }

    override fun movedToNextLexeme(): LexemeParser {
        TODO("Not yet implemented")
    }
}