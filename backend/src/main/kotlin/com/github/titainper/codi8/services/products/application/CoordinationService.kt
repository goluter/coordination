package com.github.titainper.codi8.services.products.application

import com.github.titainper.codi8.services.products.application.dto.LowestPriceByBrandResponse
import com.github.titainper.codi8.services.products.application.dto.LowestPriceByCategoryResponse
import com.github.titainper.codi8.services.products.application.dto.PriceRangeByCategoryResponse
import com.github.titainper.codi8.services.products.infrastructure.ProductRepositoryCustom
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CoordinationService(
    private val productRepositoryCustom: ProductRepositoryCustom,
) {
    @Cacheable(cacheNames = ["lowestPricesByBrand"], key = "#brand")
    fun getLowestPricesByBrand(brand: String): LowestPriceByBrandResponse {
        val categoryPrices = productRepositoryCustom.findLowestPricesByBrand(brand)
        val categories = categoryPrices.map { LowestPriceByBrandResponse.CategoryPrice(it.category, it.price.toInt()) }
        val totalPrice = categories.sumOf { it.price }
        return LowestPriceByBrandResponse(brand, categories, totalPrice)
    }

    @Cacheable(cacheNames = ["lowestPricesByCategory"])
    fun getLowestPricesByCategory(): LowestPriceByCategoryResponse {
        val categories = listOf("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")

        val categoryBrandPrices = runBlocking {
            categories.map { category ->
                async {
                    val (lowestPrice) = productRepositoryCustom.findPriceRangeByCategory(category)
                    LowestPriceByCategoryResponse.CategoryBrandPrice(
                        category = category,
                        brand = lowestPrice.brand,
                        price = lowestPrice.price.toInt()
                    )
                }
            }.map { it.await() }
        }

        val totalPrice = categoryBrandPrices.sumOf { it.price }
        return LowestPriceByCategoryResponse(categoryBrandPrices, totalPrice)
    }

    @Cacheable(cacheNames = ["priceRangeByCategory"], key = "#category")
    fun getPriceRangeByCategory(category: String): PriceRangeByCategoryResponse {
        val (lowestPrice, highestPrice) = productRepositoryCustom.findPriceRangeByCategory(category)
        return PriceRangeByCategoryResponse(
            category,
            listOf(PriceRangeByCategoryResponse.BrandPrice(lowestPrice.brand, lowestPrice.price.toInt())),
            listOf(PriceRangeByCategoryResponse.BrandPrice(highestPrice.brand, highestPrice.price.toInt()))
        )
    }
}