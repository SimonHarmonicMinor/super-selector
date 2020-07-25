package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateParser : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val ch = queryState.currentChar
        if (ch == '@') {
            val (dateString, nextState) = collectCharsWhileConditionTrue(queryState.nextCharState()) { _, curr ->
                curr != '@'
            }
            val (dateObj, lexemeType) =
                parseZonedDateTime(dateString)?.let {
                    Pair(it, LexemeType.ZONED_DATE_TIME)
                } ?: parseLocalDateTime(dateString)?.let {
                    Pair(it, LexemeType.LOCAL_DATE_TIME)
                } ?: throw LexemeParsingException(
                    queryState,
                    "Expected one of the following date patterns: $AVAILABLE_PATTERN_PLACEHOLDERS"
                )
            return LexemeParsingResult(
                lexeme = Lexeme.of(
                    lexemeType = lexemeType,
                    pointer = queryState.pointer,
                    value = dateObj
                ),
                nextState = nextState
            )
        }
        return null
    }

    private fun parseZonedDateTime(zonedDateTimeString: String): ZonedDateTime? {
        val zonedDateTimeParts = zonedDateTimeString.split(" ")
        if (zonedDateTimeParts.size != 3)
            return null
        val (datePart, timePart, zonePart) = zonedDateTimeParts
        if (!dateTimePartsMatchesThePatterns(datePart, timePart) || !TIME_ZONE_PATTERN.matches(zonePart))
            return null
        return ZonedDateTime.of(
            buildLocalDateTime(datePart, timePart),
            ZoneOffset.of(zonePart)
        )
    }

    private fun parseLocalDateTime(localDateTimeString: String): LocalDateTime? {
        val localDateTimeParts = localDateTimeString.split(" ")
        if (localDateTimeParts.size != 2)
            return null
        val (datePart, timePart) = localDateTimeParts
        if (!dateTimePartsMatchesThePatterns(datePart, timePart))
            return null
        return buildLocalDateTime(datePart, timePart)
    }

    private fun dateTimePartsMatchesThePatterns(datePart: String, timePart: String): Boolean {
        return DATE_PATTERN.matches(datePart) &&
                (TIME_MINUTES_PATTERN.matches(timePart) || TIME_SECONDS_PATTERN.matches(timePart))
    }

    private fun buildLocalDateTime(datePart: String, timePart: String): LocalDateTime {
        val timeFormat =
            if (TIME_MINUTES_PATTERN.matches(timePart))
                "hh:mm"
            else
                "hh:mm:ss"
        return LocalDateTime.parse("$datePart $timePart", DateTimeFormatter.ofPattern("dd.MM.yyyy $timeFormat"))
    }

    companion object {
        private val DATE_PATTERN = Regex.fromLiteral("\\d{2}.\\d{2}.\\d{4}")
        private val TIME_MINUTES_PATTERN = Regex.fromLiteral("\\d{2}:\\d{2}")
        private val TIME_SECONDS_PATTERN = Regex.fromLiteral("\\d{2}:\\d{2}:\\d{2}")
        private val TIME_ZONE_PATTERN = Regex.fromLiteral("(\\+\\-)\\d{2}:\\d{2}")

        private val AVAILABLE_PATTERN_PLACEHOLDERS = listOf(
            "DD.MM.YYYY",
            "DD.MM.YYYY HH:mm",
            "DD.MM.YYYY HH:mm:SS",
            "DD.MM.YYYY HH:mm ±HH:mm",
            "DD.MM.YYYY HH:mm:SS ±HH:mm"
        )
    }
}