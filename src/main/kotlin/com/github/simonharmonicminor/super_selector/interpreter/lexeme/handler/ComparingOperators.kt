package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class ComparingOperators : LexemeParser {
    override fun parseLexeme(queryState: QueryState): LexemeParsingResult? {
        val ch = queryState.currentChar
        val potentialLexemeEndState = queryState.nextCharState()
        val pair = when (ch) {
            '<' -> {
                if (potentialLexemeEndState.currentChar == '=')
                    Pair(LexemeType.LE, potentialLexemeEndState.nextCharState())
                else
                    Pair(LexemeType.LT, potentialLexemeEndState)
            }
            '>' -> {
                if (potentialLexemeEndState.currentChar == '=')
                    Pair(LexemeType.GE, potentialLexemeEndState.nextCharState())
                else
                    Pair(LexemeType.GT, potentialLexemeEndState)
            }
            '=' -> {
                Pair(LexemeType.EQ, potentialLexemeEndState)
            }
            '!' -> {
                if (potentialLexemeEndState.currentChar == '=')
                    Pair(LexemeType.NOT_EQ, potentialLexemeEndState.nextCharState())
                else
                    Pair(LexemeType.DENY, potentialLexemeEndState)
            }
            else -> null
        }

        return pair?.let { (lexemeType, nextState) ->
            LexemeParsingResult(
                lexeme = Lexeme.of(lexemeType, pointer = queryState.pointer),
                nextState = nextState
            )
        }
    }
}