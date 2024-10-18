package com.github.titainper.codi8.services.products.application.dto

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val category: String,
    val brandId: Long,
    val brandName: String
)
