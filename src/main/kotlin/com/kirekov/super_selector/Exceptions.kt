package com.kirekov.super_selector

import com.kirekov.super_selector.interpreter.Pointer
import com.kirekov.super_selector.interpreter.lexeme.QueryState

class PointerException(message: String) : RuntimeException(message)

class LexemeParsingException(val pointer: Pointer, message: String) :
        RuntimeException("Lexeme parsing error at line ${pointer.line} and column ${pointer.column}. $message") {

    constructor(queryState: QueryState, message: String) : this(
            pointer = queryState.pointer,
            message = message
    )
}

class QueryParsingException : RuntimeException {
    val pointer: Pointer

    constructor(pointer: Pointer, message: String) : this(pointer, message, null)

    constructor(message: String, cause: LexemeParsingException) : this(
            cause.pointer,
            message,
            cause
    )

    private constructor(
            pointer: Pointer,
            message: String,
            cause: LexemeParsingException?
    ) : super(
            "Grammar parsing error at line ${pointer.line} and column ${pointer.column}. $message",
            cause
    ) {
        this.pointer = pointer
    }
}

class ExpressionEvaluationException(message: String)
    : RuntimeException(message)