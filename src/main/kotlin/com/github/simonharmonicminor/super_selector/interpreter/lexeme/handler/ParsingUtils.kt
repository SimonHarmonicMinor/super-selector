package com.github.simonharmonicminor.super_selector.interpreter.lexeme.handler

private fun String.takeWhileIndexed(predicate: (Int, Char) -> Boolean): String {
    val builder = StringBuilder()
    for (i in this.indices) {
        if (!predicate(i, this[i]))
            break
        builder.append(this[i])
    }
    return builder.toString()
}

fun collectCharsWhileConditionTrue(query: String, startIndex: Int, condition: (Int, Char) -> Boolean): String {
    return query.substring(startIndex).takeWhileIndexed(condition)
}