package com.github.simonharmonicminor.super_selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*

internal class CachedResultTest {

    @Test
    fun invoke() {
        val mockObj = mock(Any::class.java)
        val func = object : InvokeFunc<Int> {
            override fun get(): Int {
                return 123
            }
        }

        val cachedResult = CachedResult(func::get)

        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        verify(mockObj, times(1)).hashCode()
    }

    private interface InvokeFunc<T> {
        fun get(): T
    }
}