package com.github.titainper.codi8.services.products.application.dto

data class PriceRangeByCategoryResponse(
    val category: String,
    val lowestPrice: List<BrandPrice>,
    val highestPrice: List<BrandPrice>
) {
    data class BrandPrice(
        val brand: String,
        val price: Int
    )
}