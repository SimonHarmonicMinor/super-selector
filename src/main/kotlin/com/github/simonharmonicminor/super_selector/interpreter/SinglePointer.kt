package com.github.simonharmonicminor.super_selector.interpreter

import com.github.simonharmonicminor.super_selector.SinglePointerException

/**
 * Represents one-based pointer in the query (line or column)
 */
class SinglePointer(index: Int) {
    val value: Int

    init {
        if (index < 0)
            throw SinglePointerException("The index should not be less than 0")
        value = index + 1
    }

    override fun toString(): String {
        return "$value"
    }
}