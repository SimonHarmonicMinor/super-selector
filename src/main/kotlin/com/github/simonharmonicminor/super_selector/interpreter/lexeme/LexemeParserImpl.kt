package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.CachedResult
import com.github.simonharmonicminor.super_selector.LexemeParsingException
import java.lang.StringBuilder

internal fun String.takeWhileIndexed(predicate: (Int, Char) -> Boolean): String {
    val builder = StringBuilder()
    for (i in this.indices) {
        if (!predicate(i, this[i]))
            break
        builder.append(this[i])
    }
    return builder.toString()
}

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
        val (lexeme, _) = nextLexemeRetriever()
        return lexeme
    }

    override fun movedToNextLexeme(): LexemeParser {
        val (_, newIndex) = nextLexemeRetriever()
        return LexemeParserImpl(query, newIndex)
    }

    private fun getNextLexeme(): Pair<Lexeme?, Int> {
        if (index >= query.length)
            return Pair(null, index)
        var currIndex = index
        while (currIndex < query.length) {
            val currChar = query[currIndex]
            if (IGNORED_SYMBOLS.contains(currChar)) {
                currIndex++
                continue
            }
            if (query[currIndex].isLetter()) {
                val identifier = collectIdentifier(currIndex)
                return Pair(Lexeme.of(LexemeType.FIELD, identifier), currIndex + identifier.length)
            }
            throw LexemeParsingException("Unexpected character at position ${currIndex + 1}: '${currChar}'")
        }
        return Pair(null, currIndex)
    }

    private fun collectIdentifier(startIndex: Int) =
        collectCharsWhileConditionTrue(startIndex) { index, ch ->
            if (index == startIndex)
                ch.isLetter()
            ch.isLetter() || ch.isDigit()
        }

    private fun collectCharsWhileConditionTrue(startIndex: Int, condition: (Int, Char) -> Boolean): String {
        return query.substring(startIndex).takeWhileIndexed(condition)
    }

    companion object {
        private val IGNORED_SYMBOLS = setOf(' ', '\t', '\n', '\r')
    }
}