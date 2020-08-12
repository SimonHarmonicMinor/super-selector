package com.kirekov.super_selector.parsed.expression

/**
 * Represents a record that should be tested by filters.
 *
 * @see Expression
 */
interface Record {
    /**
     * Returns field value.
     *
     * @throws NoSuchFieldException if the record does not contain the field with the given name
     */
    fun getFieldValue(field: String): Value
}