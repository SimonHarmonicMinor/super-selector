package com.github.simonharmonicminor.super_selector

import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class SinglePointerException(message: String) : RuntimeException(message)

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