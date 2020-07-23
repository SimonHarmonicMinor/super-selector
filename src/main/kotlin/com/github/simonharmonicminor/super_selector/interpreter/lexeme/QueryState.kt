package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.interpreter.Pointer

/**
 * The interface encapsulates the query string and provides an ability to
 * traverse the it by each character.
 *
 * [pointer] - the current pointer (line and column)
 *
 * [currentChar] - the current character (null if reached the end of the query)
 *
 * [nextCharState] - returns new [QueryState] with the pointer shifted to the next character
 */
interface QueryState {
    val pointer: Pointer
    val currentChar: Char?
    fun nextCharState(): QueryState
}