package com.kirekov.super_selector.interpreter

import com.kirekov.super_selector.parsed.ParsedQuery

interface QueryParser {
    fun parseQuery(query: String): ParsedQuery
}