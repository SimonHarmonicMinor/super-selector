package com.github.simonharmonicminor.super_selector

class CachedResult<out T>(private val supplier: () -> T) {
    private val cache = mutableListOf<T>()

    operator fun invoke(): T {
        if (cache.isEmpty()) {
            cache.add(supplier())
        }
        return cache[0]
    }
}