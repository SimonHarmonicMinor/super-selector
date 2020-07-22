package com.github.simonharmonicminor.super_selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*

internal class CachedResultTest {

    @Test
    fun invoke() {
        val mockObj = mock(Any::class.java)
        val func = {
            mockObj.hashCode()
            123
        }

        val cachedResult = CachedResult(func)

        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        assertEquals(123, cachedResult())
        verify(mockObj, times(1)).hashCode()
    }
}