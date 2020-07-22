package com.github.simonharmonicminor.super_selector.interpreter

import com.github.simonharmonicminor.super_selector.parsed.ParsedQuery

interface QueryParser {
    fun parseQuery(query: String): ParsedQuery
}