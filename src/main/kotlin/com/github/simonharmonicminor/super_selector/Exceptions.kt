package com.github.simonharmonicminor.super_selector

import java.lang.RuntimeException

class LexemeParsingException(message: String) : RuntimeException(message)

class QueryParsingException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}