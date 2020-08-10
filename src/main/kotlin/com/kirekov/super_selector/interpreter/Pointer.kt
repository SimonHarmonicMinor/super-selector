package com.kirekov.super_selector.interpreter

import com.kirekov.super_selector.PointerException
import java.util.*

class Pointer(lineIndex: Int, columnIndex: Int) {
    val line: Int
    val column: Int

    init {
        if (lineIndex < 0 || columnIndex < 0)
            throw PointerException("The indices should be less than zero. Given: lineIndex=$lineIndex, columnIndex=$columnIndex")
        line = lineIndex + 1
        column = columnIndex + 1
    }

    override fun equals(other: Any?): Boolean {
        if (other is Pointer) {
            return other.line == line && other.column == column
        }
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(line, column)
    }
}