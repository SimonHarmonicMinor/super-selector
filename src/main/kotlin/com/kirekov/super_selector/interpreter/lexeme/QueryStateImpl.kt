package com.kirekov.super_selector.interpreter.lexeme

import com.kirekov.super_selector.interpreter.Pointer

internal class QueryStateImpl private constructor(
        private val queryRows: List<String>,
        private val lineIndex: Int,
        private val columnIndex: Int
) : QueryState {
    override val pointer: Pointer
        get() = Pointer(lineIndex = lineIndex, columnIndex = columnIndex)
    override val currentChar: Char?
        get() = queryRows.getOrElse(lineIndex) { "" }.getOrNull(columnIndex)

    override fun nextCharState(): QueryState {
        if (currentChar == null)
            return this
        val (nextLineIndex, nextColumnIndex) = nextIndices(this)
        return QueryStateImpl(queryRows, nextLineIndex, nextColumnIndex)
    }

    companion object {
        fun of(query: String): QueryState {
            val queryRows = query.split("\n")
            val (lineIndex, columnIndex) = nextIndices(queryRows, 0, -1)
            return QueryStateImpl(queryRows, lineIndex, columnIndex)
        }

        private fun nextIndices(q: QueryStateImpl) = nextIndices(q.queryRows, q.lineIndex, q.columnIndex)

        private fun nextIndices(queryRows: List<String>, lineIndex: Int, columnIndex: Int): Pair<Int, Int> {
            if (lineIndex >= queryRows.size) {
                return Pair(lineIndex, columnIndex)
            }
            if (lineIndex < queryRows.size && columnIndex + 1 < queryRows[lineIndex].length) {
                return Pair(lineIndex, columnIndex + 1)
            }
            var newLineIndex = lineIndex + 1
            while (newLineIndex < queryRows.size && queryRows[newLineIndex].isEmpty()) {
                newLineIndex++
            }
            return Pair(newLineIndex, 0)
        }
    }
}