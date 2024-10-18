package com.github.titainper.codi8.services.products.application.dto

data class CreateProductRequest(
    val name: String,
    val price: Long,
    val category: String,
    val brandId: Long
)