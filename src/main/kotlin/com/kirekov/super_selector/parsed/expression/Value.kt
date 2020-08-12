package com.kirekov.super_selector.parsed.expression

import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * Represents a value that is bounded to the field
 */
sealed class Value {
    abstract val item: Any?
}

class StringValue(override val item: String) : Value()
class DecimalValue(override val item: Long) : Value()
class LocalDateTimeValue(override val item: LocalDateTime) : Value()
class ZonedDateTimeValue(override val item: ZonedDateTime) : Value()
class BooleanValue(override val item: Boolean) : Value()
class NullValue(override val item: Any? = null) : Value()