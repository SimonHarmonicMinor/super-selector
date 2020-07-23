package com.github.simonharmonicminor.super_selector.interpreter

import com.github.simonharmonicminor.super_selector.PointerException

class Pointer(lineIndex: Int, columnIndex: Int) {
    val line: Int
    val column: Int

    init {
        if (lineIndex < 0 || columnIndex < 0)
            throw PointerException("The indices should be less than zero. Given: lineIndex=$lineIndex, columnIndex=$columnIndex")
        line = lineIndex + 1
        column = columnIndex + 1
    }
}