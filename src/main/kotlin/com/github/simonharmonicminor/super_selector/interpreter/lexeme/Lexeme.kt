package com.github.simonharmonicminor.super_selector.interpreter.lexeme

interface Lexeme {
    val lexemeType: LexemeType
    val value: Any

    companion object {
        fun of(lexemeType: LexemeType, value: Any = ""): Lexeme =
            object : Lexeme {
                override val lexemeType = lexemeType
                override val value = value
            }
    }
}
