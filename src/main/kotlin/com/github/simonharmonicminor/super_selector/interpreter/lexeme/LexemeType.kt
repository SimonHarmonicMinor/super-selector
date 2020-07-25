package com.github.simonharmonicminor.super_selector.interpreter.lexeme

enum class LexemeType(val placeholder: String) {
    SELECT("select"),
    STAR("*"),
    WHERE("where"),
    ORDER("order"),
    BY("by"),
    ASC("asc"),
    DESC("desc"),
    NULL("null"),
    TRUE("true"),
    FALSE("false"),
    NOT("not"),
    IN("in"),
    IS("is"),

    AND("&&"), OR("||"), DENY("!"),

    LE("<="), LT("<"), EQ("="),
    NOT_EQ("!="), GT(">"), GE(">="),

    LEFT_ROUND_BRACKET("("), RIGHT_ROUND_BRACKET(")"),
    LEFT_SQUARE_BRACKET("["), RIGHT_SQUARE_BRACKET("]"),

    STRING("string"), DECIMAL("decimal"), DOUBLE("double"),
    LOCAL_DATE_TIME("localDateTime"),
    ZONED_DATE_TIME("zonedDateTime"),
    FIELD("field"),
    COMMA(","), DOT(".");
}