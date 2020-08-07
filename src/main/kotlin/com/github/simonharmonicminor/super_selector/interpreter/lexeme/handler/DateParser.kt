package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState
import java.time.*
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
                nextState = nextState.nextCharState()
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
        if (localDateTimeParts.size == 2) {
            val (datePart, timePart) = localDateTimeParts
            if (!dateTimePartsMatchesThePatterns(datePart, timePart))
                return null
            return buildLocalDateTime(datePart, timePart)
        } else if (localDateTimeParts.size == 1) {
            val (datePart) = localDateTimeParts
            if (!datePartMatchesThePattern(datePart))
                return null
            return LocalDateTime.of(buildLocalDate(datePart), LocalTime.MIN)
        }
        return null
    }

    private fun dateTimePartsMatchesThePatterns(datePart: String, timePart: String) =
        datePartMatchesThePattern(datePart) &&
                (TIME_MINUTES_PATTERN.matches(timePart) || TIME_SECONDS_PATTERN.matches(timePart))

    private fun datePartMatchesThePattern(datePart: String) = DATE_PATTERN.matches(datePart)

    private fun buildLocalDate(datePart: String) = LocalDate.parse(datePart, getLocalDateFormatter())

    private fun buildLocalDateTime(datePart: String, timePart: String): LocalDateTime {
        val timeFormat =
            if (TIME_MINUTES_PATTERN.matches(timePart))
                "HH:mm"
            else
                "HH:mm:ss"
        return LocalDateTime.parse("$datePart $timePart", getLocalDateTimeFormatter(timeFormat))
    }

    private fun getLocalDateTimeFormatter(timeFormat: String) =
        DateTimeFormatter.ofPattern("${getLocalDatePattern()} $timeFormat")

    private fun getLocalDateFormatter() = DateTimeFormatter.ofPattern(getLocalDatePattern())

    private fun getLocalDatePattern() = "dd.MM.yyyy"

    companion object {
        private val DATE_PATTERN = "\\d{2}\\.\\d{2}\\.\\d{4}".toRegex()
        private val TIME_MINUTES_PATTERN = "\\d{2}:\\d{2}".toRegex()
        private val TIME_SECONDS_PATTERN = "\\d{2}:\\d{2}:\\d{2}".toRegex()
        private val TIME_ZONE_PATTERN = "([+\\-])\\d{2}:\\d{2}".toRegex()

        private val AVAILABLE_PATTERN_PLACEHOLDERS = listOf(
            "DD.MM.YYYY",
            "DD.MM.YYYY HH:mm",
            "DD.MM.YYYY HH:mm:SS",
            "DD.MM.YYYY HH:mm ±HH:mm",
            "DD.MM.YYYY HH:mm:SS ±HH:mm"
        )
    }
}