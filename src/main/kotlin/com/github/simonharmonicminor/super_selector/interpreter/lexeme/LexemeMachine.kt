package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.FieldParserHandler

/**
 * Provides an ability to parse lexemes in the query string.
 *
 * Though the class is immutable it's **not** thread-safe.
 */
interface LexemeMachine {
    /**
     * Returns next lexeme in the query string. If the pointer reaches the end of the query, returns `null`.
     * @throws LexemeParsingException if the lexeme cannot be parsed or if the pointer reached the end of the query
     */
    fun peekNextLexeme(): Lexeme

    /**
     * Returns the new instance of [LexemeMachine] with the pointer moved to the next lexeme.
     * @throws LexemeParsingException if cannot move the pointer
     */
    fun movedToNextLexeme(): LexemeMachine

    companion object {
        fun of(query: String): LexemeMachine = LexemeMachineImpl(query, FieldParserHandler(null))
    }
}