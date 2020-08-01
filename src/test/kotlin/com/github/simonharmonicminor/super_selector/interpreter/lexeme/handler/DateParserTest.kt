package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testEquivalence
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler.LexemeParserTestUtils.testNullLexemeParsingResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

internal class DateParserTest {
    @Test
    fun parseLocalDate() {
        val lexemeParsingResult =
            testEquivalence(DateParser(), LexemeType.LOCAL_DATE_TIME, *"@21.12.2012@".toCharArray())
        assertEquals(
            LocalDate.of(2012, 12, 21),
            (lexemeParsingResult!!.lexeme.value as LocalDateTime).toLocalDate()
        )
    }

    @Test
    fun throwsExceptionIfParseLocalDateWithWrongPattern() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(
                DateParser(),
                LexemeType.LOCAL_DATE_TIME,
                *"@21-12-2012@".toCharArray()
            )
        }
    }

    @Test
    fun parseLocalDateTimeMinutes() {
        val lexemeParsingResult =
            testEquivalence(DateParser(), LexemeType.LOCAL_DATE_TIME, *"@10.03.1999 12:34@".toCharArray())
        assertEquals(
            LocalDateTime.of(1999, 3, 10, 12, 34),
            lexemeParsingResult!!.lexeme.value
        )
    }

    @Test
    fun throwsIfParseLocalDateTimeMinutesWithWrongPattern() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(
                DateParser(),
                LexemeType.LOCAL_DATE_TIME,
                *"@10.03.1999 12-34@".toCharArray()
            )
        }
    }

    @Test
    fun parseLocalDateWithSecondsTime() {
        val lexemeParsingResult =
            testEquivalence(DateParser(), LexemeType.LOCAL_DATE_TIME, *"@10.03.1999 12:34:12@".toCharArray())
        assertEquals(
            LocalDateTime.of(1999, 3, 10, 12, 34, 12),
            lexemeParsingResult!!.lexeme.value
        )
    }

    @Test
    fun throwsIfParseLocalDateTimeSecondsWithWrongPattern() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(
                DateParser(),
                LexemeType.LOCAL_DATE_TIME,
                *"@10.03.1999 12-34-45@".toCharArray()
            )
        }
    }

    @Test
    fun parseZonedDateTimeWithMinutesTime() {
        val lexemeParsingResult =
            testEquivalence(DateParser(), LexemeType.ZONED_DATE_TIME, *"@14.02.1993 23:33 +15:23@".toCharArray())
        val localDateTime = LocalDateTime.of(1993, 2, 14, 23, 33)
        assertEquals(
            ZonedDateTime.of(localDateTime, ZoneOffset.of("+15:23")),
            lexemeParsingResult!!.lexeme.value
        )
    }

    @Test
    fun parseZonedDateTimeWithSecondsTime() {
        val lexemeParsingResult =
            testEquivalence(DateParser(), LexemeType.ZONED_DATE_TIME, *"@14.02.1993 23:33:10 +15:23@".toCharArray())
        val localDateTime = LocalDateTime.of(1993, 2, 14, 23, 33, 10)
        assertEquals(
            ZonedDateTime.of(localDateTime, ZoneOffset.of("+15:23")),
            lexemeParsingResult!!.lexeme.value
        )
    }

    @Test
    fun throwsExceptionIfParseZonedDateTimeWithWrongTimeZone() {
        assertThrows(LexemeParsingException::class.java) {
            testEquivalence(DateParser(), LexemeType.ZONED_DATE_TIME, *"@14.02.1993 23:33:10 +15-23@".toCharArray())
        }
    }

    @Test
    fun unexpectedCharacter() {
        testNullLexemeParsingResult(DateParser(), '>')
    }
}