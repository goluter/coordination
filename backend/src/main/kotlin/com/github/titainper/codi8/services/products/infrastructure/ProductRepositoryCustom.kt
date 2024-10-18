package com.github.titainper.codi8.services.products.infrastructure

import com.github.titainper.codi8.services.products.infrastructure.dto.ProductDto

interface ProductRepositoryCustom {
    fun findLowestPricesByBrand(brand: String): List<ProductDto>
    fun findPriceRangeByCategory(category: String): Pair<ProductDto, ProductDto>
}
