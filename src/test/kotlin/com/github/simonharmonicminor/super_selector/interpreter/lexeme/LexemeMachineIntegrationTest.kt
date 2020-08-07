package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.Pointer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime

class LexemeMachineIntegrationTest {
    @Test
    fun parseBrackets() {
        var lexemeMachine = LexemeMachine.of("( )  \n\r \t ] [")
        val leftRoundBracket = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val rightRoundBracket =
            lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val rightSquareBracket =
            lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val leftSquareBracket =
            lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.LEFT_ROUND_BRACKET, leftRoundBracket.lexemeType)
        assertEquals(Pointer(0, 0), leftRoundBracket.pointer)

        assertEquals(LexemeType.RIGHT_ROUND_BRACKET, rightRoundBracket.lexemeType)
        assertEquals(Pointer(0, 2), rightRoundBracket.pointer)

        assertEquals(LexemeType.RIGHT_SQUARE_BRACKET, rightSquareBracket.lexemeType)
        assertEquals(Pointer(1, 4), rightSquareBracket.pointer)

        assertEquals(LexemeType.LEFT_SQUARE_BRACKET, leftSquareBracket.lexemeType)
        assertEquals(Pointer(1, 6), leftSquareBracket.pointer)

        assertThrows<LexemeParsingException> { lexemeMachine.peekNextLexeme() }
    }

    @Test
    fun parseComparingOperators() {
        var lexemeMachine = LexemeMachine.of("> \n   >= \n \n  < <= \n = !! !=")
        val gt = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val ge = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val lt = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val le = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val eq = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val deny1 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val deny2 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val notEq = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.GT, gt.lexemeType)
        assertEquals(Pointer(0, 0), gt.pointer)

        assertEquals(LexemeType.GE, ge.lexemeType)
        assertEquals(Pointer(1, 3), ge.pointer)

        assertEquals(LexemeType.LT, lt.lexemeType)
        assertEquals(Pointer(3, 2), lt.pointer)

        assertEquals(LexemeType.LE, le.lexemeType)
        assertEquals(Pointer(3, 4), le.pointer)

        assertEquals(LexemeType.EQ, eq.lexemeType)
        assertEquals(Pointer(4, 1), eq.pointer)

        assertEquals(LexemeType.DENY, deny1.lexemeType)
        assertEquals(Pointer(4, 3), deny1.pointer)

        assertEquals(LexemeType.DENY, deny2.lexemeType)
        assertEquals(Pointer(4, 4), deny2.pointer)

        assertEquals(LexemeType.NOT_EQ, notEq.lexemeType)
        assertEquals(Pointer(4, 6), notEq.pointer)
    }

    @Test
    fun parseDate() {
        var lexemeMachine = LexemeMachine.of("   @21.12.2018@ \n \r\t   @12.08.1998 12:00:34@ ")
        val date = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val dateTime = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.LOCAL_DATE_TIME, date.lexemeType)
        assertEquals(Pointer(0, 3), date.pointer)
        assertEquals(LocalDate.of(2018, 12, 21), (date.value as LocalDateTime).toLocalDate())

        assertEquals(LexemeType.LOCAL_DATE_TIME, dateTime.lexemeType)
        assertEquals(Pointer(1, 6), dateTime.pointer)
        assertEquals(LocalDateTime.of(1998, 8, 12, 12, 0, 34), dateTime.value)

        assertThrows<LexemeParsingException> { lexemeMachine.peekNextLexeme() }
    }

    @Test
    fun parseFields() {
        var lexemeMachine = LexemeMachine.of(" rtr ___ _underScore12_  \n another_under_score \n\n `select` `where` ")
        val field1 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val field2 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val field3 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val field4 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val field5 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val field6 = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.FIELD, field1.lexemeType)
        assertEquals(Pointer(0, 1), field1.pointer)
        assertEquals("rtr", field1.value)

        assertEquals(LexemeType.FIELD, field2.lexemeType)
        assertEquals(Pointer(0, 5), field2.pointer)
        assertEquals("___", field2.value)

        assertEquals(LexemeType.FIELD, field3.lexemeType)
        assertEquals(Pointer(0, 9), field3.pointer)
        assertEquals("_underScore12_", field3.value)

        assertEquals(LexemeType.FIELD, field4.lexemeType)
        assertEquals(Pointer(1, 1), field4.pointer)
        assertEquals("another_under_score", field4.value)

        assertEquals(LexemeType.FIELD, field5.lexemeType)
        assertEquals(Pointer(3, 1), field5.pointer)
        assertEquals("select", field5.value)

        assertEquals(LexemeType.FIELD, field6.lexemeType)
        assertEquals(Pointer(3, 10), field6.pointer)
        assertEquals("where", field6.value)

        assertThrows<LexemeParsingException> { lexemeMachine.peekNextLexeme() }
    }

    @Test
    fun parseLogicalOperators() {
        var lexemeMachine = LexemeMachine.of("  \n\n &&   \n||  ")
        val and = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val or = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.AND, and.lexemeType)
        assertEquals(3, and.pointer.line)
        assertEquals(2, and.pointer.column)

        assertEquals(LexemeType.OR, or.lexemeType)
        assertEquals(4, or.pointer.line)
        assertEquals(1, or.pointer.column)

        assertThrows<LexemeParsingException> { lexemeMachine.peekNextLexeme() }
    }

    @Test
    fun parseOrOperatorFails() {
        parseFails("  | ")
    }

    @Test
    fun parseAndOperatorFails() {
        parseFails(" &  ")
    }

    @Test
    fun parseNumbers() {
        var lexemeMachine = LexemeMachine.of("\n1002  \n\t\r 9231.342 \n -123 -98.023 \n \n 0")
        val firstNumber = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val secondNumber = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val thirdNumber = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val fourthNumber = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val fifthNumber = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.DECIMAL, firstNumber.lexemeType)
        assertEquals(2, firstNumber.pointer.line)
        assertEquals(1, firstNumber.pointer.column)
        assertEquals(1002L, firstNumber.value)

        assertEquals(LexemeType.DOUBLE, secondNumber.lexemeType)
        assertEquals(3, secondNumber.pointer.line)
        assertEquals(4, secondNumber.pointer.column)
        assertEquals(9231.342, secondNumber.value)

        assertEquals(LexemeType.DECIMAL, thirdNumber.lexemeType)
        assertEquals(4, thirdNumber.pointer.line)
        assertEquals(2, thirdNumber.pointer.column)
        assertEquals(-123L, thirdNumber.value)

        assertEquals(LexemeType.DOUBLE, fourthNumber.lexemeType)
        assertEquals(4, fourthNumber.pointer.line)
        assertEquals(7, fourthNumber.pointer.column)
        assertEquals(-98.023, fourthNumber.value)

        assertEquals(LexemeType.DECIMAL, fifthNumber.lexemeType)
        assertEquals(6, fifthNumber.pointer.line)
        assertEquals(2, fifthNumber.pointer.column)
        assertEquals(0L, fifthNumber.value)
    }

    @Test
    fun parseDoubleFails() {
        parseFails("  454. ")
    }

    @Test
    fun parseNegativeDecimalFails() {
        parseFails(" --123  ")
    }

    @Test
    fun parseStrings() {
        var lexemeMachine = LexemeMachine.of(" \"str1\"  \n\n  'str2' ")
        val doubleQuotedString = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }
        val singleQuotedString = lexemeMachine.peekNextLexeme().also { lexemeMachine = lexemeMachine.movedToNextLexeme() }

        assertEquals(LexemeType.STRING, doubleQuotedString.lexemeType)
        assertEquals(1, doubleQuotedString.pointer.line)
        assertEquals(2, doubleQuotedString.pointer.column)
        assertEquals("str1", doubleQuotedString.value)

        assertEquals(LexemeType.STRING, singleQuotedString.lexemeType)
        assertEquals(3, singleQuotedString.pointer.line)
        assertEquals(3, singleQuotedString.pointer.column)
        assertEquals("str2", singleQuotedString.value)
    }

    @Test
    fun parseSingleQuotedStringFails() {
        parseFails("  'dasdasf ")
    }

    @Test
    fun parseDoubleQuotedStringFails() {
        parseFails("  \"bfbg ")
    }

    private fun parseFails(query: String) {
        assertThrows<LexemeParsingException> {
            LexemeMachine.of(query).peekNextLexeme()
        }
    }
}