package com.kirekov.super_selector.interpreter.lexeme

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class QueryStateImplTest {

    @Test
    fun ifQueryIsEmptyCurrentCharIsNull() {
        val queryState = QueryStateImpl.of("")
        assertNull(queryState.currentChar)
    }

    @Test
    fun returnsExpectedCharacters() {
        var queryState: QueryState = QueryStateImpl.of("abcd fg")
        val a = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val b = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val c = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val d = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val space = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val f = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val g = queryState.currentChar.also { queryState = queryState.nextCharState() }
        val nullCharacter = queryState.currentChar.also { queryState = queryState.nextCharState() }

        assertEquals('a', a)
        assertEquals('b', b)
        assertEquals('c', c)
        assertEquals('d', d)
        assertEquals(' ', space)
        assertEquals('f', f)
        assertEquals('g', g)
        assertEquals(null, nullCharacter)
        assertEquals(null, queryState.currentChar)
    }

    @Test
    fun queryStatePointsToTheRightPosition() {
        var queryState: QueryState = QueryStateImpl.of("ab \n cd  \t \nef")
        val aState = queryState.also { queryState = queryState.nextCharState() }
        val bState = queryState.also {
            queryState = queryState.nextCharState()
                    .nextCharState()
                    .nextCharState()
        }
        val cState = queryState.also { queryState = queryState.nextCharState() }
        val dState = queryState.also {
            queryState = queryState.nextCharState()
                    .nextCharState()
                    .nextCharState()
                    .nextCharState()
                    .nextCharState()
        }
        val eState = queryState.also { queryState = queryState.nextCharState() }
        val fState = queryState.also { queryState = queryState.nextCharState() }

        assertEquals('a', aState.currentChar)
        assertEquals(1, aState.pointer.line)
        assertEquals(1, aState.pointer.column)

        assertEquals('b', bState.currentChar)
        assertEquals(1, bState.pointer.line)
        assertEquals(2, bState.pointer.column)

        assertEquals('c', cState.currentChar)
        assertEquals(2, cState.pointer.line)
        assertEquals(2, cState.pointer.column)

        assertEquals('d', dState.currentChar)
        assertEquals(2, dState.pointer.line)
        assertEquals(3, dState.pointer.column)

        assertEquals('e', eState.currentChar)
        assertEquals(3, eState.pointer.line)
        assertEquals(1, eState.pointer.column)

        assertEquals('f', fState.currentChar)
        assertEquals(3, fState.pointer.line)
        assertEquals(2, fState.pointer.column)
    }

    @Test
    fun skipsEmptyLines() {
        var queryState: QueryState = QueryStateImpl.of("\n\n\nrt\n\ny")
        val rState = queryState.also { queryState = queryState.nextCharState() }
        val tState = queryState.also { queryState = queryState.nextCharState() }
        val yState = queryState.also { queryState = queryState.nextCharState() }

        assertEquals('r', rState.currentChar)
        assertEquals(4, rState.pointer.line)
        assertEquals(1, rState.pointer.column)

        assertEquals('t', tState.currentChar)
        assertEquals(4, tState.pointer.line)
        assertEquals(2, tState.pointer.column)

        assertEquals('y', yState.currentChar)
        assertEquals(6, yState.pointer.line)
        assertEquals(1, yState.pointer.column)
    }
}