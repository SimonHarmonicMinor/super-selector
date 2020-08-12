package com.kirekov.super_selector.parsed.order

/**
 * Defines the ordering rule
 */
interface OrderingRule {
    val order: Order
    val fieldName: String
}
