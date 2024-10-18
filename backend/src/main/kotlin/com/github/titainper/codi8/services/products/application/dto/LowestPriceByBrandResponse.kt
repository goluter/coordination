package com.github.titainper.codi8.services.products.application.dto

data class LowestPriceByBrandResponse(
    val brand: String,
    val categories: List<CategoryPrice>,
    val totalPrice: Int
) {
    data class CategoryPrice(
        val category: String,
        val price: Int
    )
}
