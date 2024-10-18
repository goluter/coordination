package com.github.titainper.codi8.services.products.application.dto

data class LowestPriceByCategoryResponse(
    val categories: List<CategoryBrandPrice>,
    val totalPrice: Int
) {
    data class CategoryBrandPrice(
        val category: String,
        val brand: String,
        val price: Int
    )
}
