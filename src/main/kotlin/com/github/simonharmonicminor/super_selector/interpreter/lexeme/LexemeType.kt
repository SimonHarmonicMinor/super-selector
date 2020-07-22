package com.github.simonharmonicminor.super_selector.interpreter.lexeme

enum class LexemeType {
    SELECT, STAR, WHERE, ORDER, BY, ASC, DESC, NULL, TRUE, FALSE, NOT, IN, IS,
    AND, OR, DENY,
    LE, LT, EQ, NOT_EQ, GT, GE,
    LEFT_ROUND_BRACKET, RIGHT_ROUND_BRACKET,
    LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET,
    STRING, DECIMAL, DOUBLE, DATE,
    FIELD,
    COMMA, DOT
}