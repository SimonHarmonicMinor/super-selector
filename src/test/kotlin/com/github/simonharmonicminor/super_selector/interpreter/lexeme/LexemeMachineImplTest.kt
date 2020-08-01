package com.github.simonharmonicminor.super_selector.interpreter.lexeme

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class LexemeMachineImplTest {
    @Test
    fun throwsLexemeParsingExceptionOnTryToPeekLexemeIfNothingIsPresent() {
        val parser = LexemeMachine.of("")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun throwsLexemeParsingExceptionIfFractionalPartAfterDotIsMissing() {
        val parser = LexemeMachine.of("   \n\t 10013.  ")
        assertThrows(LexemeParsingException::class.java) { parser.peekNextLexeme() }
    }

    @Test
    fun throwsExceptionIfStuckIntoUnexpectedCharacter() {
        val parser = LexemeMachine.of(" > < = $ ")
        assertThrows(LexemeParsingException::class.java) {
            parser.movedToNextLexeme()
                .movedToNextLexeme()
                .movedToNextLexeme()
                .peekNextLexeme()
        }
    }
}