package com.github.simonharmonicminor.super_selector.order

/**
 * Defines the ordering rule
 */
interface OrderingRule {
    val order: Order
    val fieldName: String
}
