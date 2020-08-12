package com.kirekov.super_selector.parsed.expression

import com.kirekov.super_selector.ExpressionEvaluationException

/**
 * Represents the result expression that holds the filtering rules.
 *
 * [children] - list of children expressions
 *
 * [evaluate] - evaluates the expression for the given record
 *
 * @see Record
 */
sealed class Expression {
    abstract val children: List<Expression>

    /**
     * Returns new expression that has a reversed condition
     */
    abstract fun not(): Expression

    /**
     * Evaluates the given record for the expression and all its children.
     *
     * @return <code>true</code> if the expression evaluated successfully
     * @param record the entity that should be tested
     * @throws ExpressionEvaluationException if something went wrong
     */
    abstract fun evaluate(record: Record): Boolean
}

class AndExpression private constructor(override val children: List<Expression>,
                                        private val negative: Boolean) : Expression() {
    companion object {
        fun new(children: List<Expression>) = AndExpression(children, false)
        fun newNot(children: List<Expression>) = AndExpression(children, true)
    }

    override fun evaluate(record: Record): Boolean {
        val res = children.all { it.evaluate(record) }
        return if (negative) !res else res
    }

    override fun not() = AndExpression(
            children,
            !negative
    )

}

class OrExpression private constructor(override val children: List<Expression>,
                                       private val negative: Boolean) : Expression() {
    companion object {
        fun new(children: List<Expression>) = OrExpression(children, false)
        fun newNot(children: List<Expression>) = OrExpression(children, true)
    }

    override fun evaluate(record: Record): Boolean {
        val res = children.any { it.evaluate(record) }
        return if (negative) !res else res
    }

    override fun not() = OrExpression(
            children,
            !negative
    )
}

/**
 * Represents terminal that holds the particular field
 */
sealed class Equation : Expression() {
    abstract val field: String
    final override val children = listOf<Expression>()
}