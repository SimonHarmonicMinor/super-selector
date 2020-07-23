package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.interpreter.Pointer

internal class QueryStateImpl : QueryState {
    private val queryRows: List<String>
    private val lineIndex: Int
    private val columnIndex: Int
    override val pointer: Pointer
        get() = Pointer(lineIndex = lineIndex, columnIndex = columnIndex)
    override val currentChar: Char?
        get() = queryRows.getOrElse(lineIndex) { "" }.getOrNull(columnIndex)

    constructor(query: String) : this(query.split("\n"), 0, 0)

    private constructor(queryRows: List<String>, lineIndex: Int, columnIndex: Int) {
        this.queryRows = queryRows
        this.lineIndex = lineIndex
        this.columnIndex = columnIndex
    }

    override fun nextCharState(): QueryState {
        if (currentChar == null)
            return this
        if (columnIndex + 1 < queryRows[lineIndex].length || lineIndex == queryRows.size - 1)
            return QueryStateImpl(queryRows, lineIndex, columnIndex + 1)
        return QueryStateImpl(queryRows, lineIndex + 1, 0)
    }
}