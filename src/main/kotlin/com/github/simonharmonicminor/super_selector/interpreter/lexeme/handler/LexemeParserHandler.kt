package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult

private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')

abstract class LexemeParserHandler {
    protected abstract val next: LexemeParserHandler?

    protected abstract fun innerParseLexeme(query: String, startIndex: Int): LexemeParsingResult?

    fun parseLexeme(query: String, startIndex: Int): LexemeParsingResult {
        var index = startIndex
        while (IGNORED_SYMBOLS.contains(query[index])) {
            index++
        }
        val result = innerParseLexeme(query, index)
        if (result != null)
            return result
        return next?.parseLexeme(query, index)
            ?: throw LexemeParsingException("Unexpected symbol at $index position")
    }
}
