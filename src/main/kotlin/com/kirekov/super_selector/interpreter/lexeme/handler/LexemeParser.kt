package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.interpreter.lexeme.LexemeParsingResult
import com.kirekov.super_selector.interpreter.lexeme.QueryState

/**
 * Represents a lexeme parser.
 */
interface LexemeParser {
    /**
     * Accepts [queryState] and returns parsed result or `null` if no lexeme is detected
     */
    fun parseLexeme(queryState: QueryState): LexemeParsingResult?
}
