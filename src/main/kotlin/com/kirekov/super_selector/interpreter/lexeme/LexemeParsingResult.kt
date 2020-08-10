package com.kirekov.super_selector.interpreter.lexeme

data class LexemeParsingResult(val lexeme: Lexeme, val nextState: QueryState)