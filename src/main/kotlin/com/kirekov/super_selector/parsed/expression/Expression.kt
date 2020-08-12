package com.kirekov.super_selector.parsed.expression

import com.kirekov.super_selector.ExpressionEvaluationException

/**
 * Represents the result expression that holds the filtering rules.
 *
 * [children] - list of children expressions
 *
 * [evaluate] - evaluates the expression for the given record
 *
 * @see JoiningType
 * @see Record
 */
sealed class Expression {
    abstract val children: List<Expression>

    /**
     * Evaluates the given record for the expression and all its children.
     *
     * @return <code>true</code> if the expression evaluated successfully
     * @param record the entity that should be tested
     * @throws ExpressionEvaluationException if something went wrong
     */
    abstract fun evaluate(record: Record): Boolean
}

class AndExpression(override val children: List<Expression>) : Expression() {
    override fun evaluate(record: Record) = children.all { it.evaluate(record) }
}

class OrExpression(override val children: List<Expression>) : Expression() {
    override fun evaluate(record: Record) = children.any { it.evaluate(record) }
}

/**
 * Represents terminal that holds the particular field
 */
sealed class Equation : Expression() {
    abstract val field: String
    final override val children = listOf<Expression>()
}