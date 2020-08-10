package com.kirekov.super_selector

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CachedResultTest {

    @Test
    fun invoke() {
        val func = object : InvokeFunc<Int> {
            private var counter = 1

            override fun get(): Int {
                return counter++
            }
        }

        val cachedResult = CachedResult(func::get)

        assertEquals(1, cachedResult())
        assertEquals(1, cachedResult())
        assertEquals(1, cachedResult())
        assertEquals(1, cachedResult())
    }

    private interface InvokeFunc<T> {
        fun get(): T
    }
}