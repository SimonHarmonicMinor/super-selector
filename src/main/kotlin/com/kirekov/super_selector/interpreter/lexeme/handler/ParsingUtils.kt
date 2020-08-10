package com.kirekov.super_selector.interpreter.lexeme.handler

import com.kirekov.super_selector.interpreter.Pointer
import com.kirekov.super_selector.interpreter.lexeme.QueryState


fun collectCharsWhileConditionTrue(
        queryState: QueryState,
        condition: (Pointer, Char) -> Boolean
): Pair<String, QueryState> {
    val builder = StringBuilder()
    var localQueryState = queryState
    val localCondition: (Pointer, Char?) -> Boolean = { pointer, ch ->
        ch?.let { condition(pointer, ch) } ?: false
    }
    while (localCondition(localQueryState.pointer, localQueryState.currentChar)) {
        builder.append(localQueryState.currentChar)
        localQueryState = localQueryState.nextCharState()
    }
    return Pair(builder.toString(), localQueryState)
}