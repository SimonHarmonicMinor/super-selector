package com.kirekov.super_selector.interpreter.lexeme

import com.kirekov.super_selector.interpreter.Pointer

interface Lexeme {
    val lexemeType: LexemeType
    val pointer: Pointer
    val value: Any

    companion object {
        fun of(lexemeType: LexemeType, pointer: Pointer, value: Any = ""): Lexeme =
                object : Lexeme {
                    override val lexemeType = lexemeType
                    override val pointer = pointer
                    override val value = value
                }
    }
}
