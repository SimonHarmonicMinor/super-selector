package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

interface LexemeParser {
    fun parseLexeme(queryState: QueryState): LexemeParsingResult?
}
