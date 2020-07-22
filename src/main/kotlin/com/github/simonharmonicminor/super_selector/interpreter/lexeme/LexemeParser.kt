package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException

/**
 * Provides an ability to parse lexemes in the query string.
 *
 * Though the class is immutable it's **not** thread-safe.
 */
interface LexemeParser {
    /**
     * Returns next lexeme in the query string. If the pointer reaches the end of the query, returns `null`.
     * @throws LexemeParsingException if the lexeme cannot be parsed
     */
    fun peekNextLexeme(): Lexeme?

    /**
     * Returns the new instance of [LexemeParser] with the pointer moved to the next lexeme.
     * @throws LexemeParsingException if cannot move the pointer
     */
    fun movedToNextLexeme(): LexemeParser

    companion object {
        fun of(query: String): LexemeParser = LexemeParserImpl(query)
    }
}