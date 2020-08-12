package com.kirekov.super_selector.parsed

import com.kirekov.super_selector.parsed.order.OrderingRule


/**
 * Defines the result of the parsing.
 *
 * [expression] – the filtering predicate.
 *
 * [fields] – the fields that should be selected. If nothing is given, returns empty list.
 *
 * [orderingRules] – the ordering rules. If nothing is given, returns empty list.
 */
interface ParsedQuery {
    val expression: Expression
    val fields: List<String>
    val orderingRules: List<OrderingRule>
}