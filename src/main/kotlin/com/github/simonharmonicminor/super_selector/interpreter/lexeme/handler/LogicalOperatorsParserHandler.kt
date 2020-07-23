package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

import com.github.simonharmonicminor.super_selector.LexemeParsingException
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.Lexeme
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeParsingResult
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.LexemeType
import com.github.simonharmonicminor.super_selector.interpreter.lexeme.QueryState

class LogicalOperatorsParserHandler(next: LexemeParserHandler?) : LexemeParserHandler(next) {
    override fun innerParseLexeme(queryState: QueryState): LexemeParsingResult? {
        val pair = when (queryState.currentChar) {
            '&' -> Pair('&', LexemeType.AND)
            '|' -> Pair('|', LexemeType.OR)
            '!' -> Pair(null, LexemeType.DENY)
            else -> null
        }
        val potentialNextState = queryState.nextCharState()
        return pair?.let { (ch, lexemeType) ->
            val nextQueryState = ch?.let {
                if (potentialNextState.currentChar != it) {
                    throw LexemeParsingException(potentialNextState, "Expected '$it' character")
                }
                potentialNextState.nextCharState()
            } ?: potentialNextState
            LexemeParsingResult(
                lexeme = Lexeme.of(lexemeType = lexemeType, pointer = nextQueryState.pointer),
                nextState = nextQueryState
            )
        }
    }
}