package com.github.titainper.codi8.services.products.application.dto

data class UpdateProductRequest(
    val name: String?,
    val price: Long?,
    val category: String?
)