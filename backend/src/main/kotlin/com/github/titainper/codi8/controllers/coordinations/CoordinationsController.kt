package com.github.titainper.codi8.controllers.v1

import com.github.titainper.codi8.services.products.application.CoordinationService
import com.github.titainper.codi8.services.products.application.dto.LowestPriceByBrandResponse
import com.github.titainper.codi8.services.products.application.dto.LowestPriceByCategoryResponse
import com.github.titainper.codi8.services.products.application.dto.PriceRangeByCategoryResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coordination")
class CoordinationController(private val coordinationService: CoordinationService) {
    // NOTE: 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    @GetMapping("/category-lowest-price")
    fun getLowestPricesByCategory(): LowestPriceByCategoryResponse {
        return coordinationService.getLowestPricesByCategory()
    }

    // NOTE: 2. 단일 브랜드 최저가
    @GetMapping("/brand-lowest-prices")
    fun getLowestPricesByBrand(@RequestParam brand: String): LowestPriceByBrandResponse {
        return coordinationService.getLowestPricesByBrand(brand)
    }

    // NOTE: 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    @GetMapping("/price-range")
    fun getPriceRangeByCategory(@RequestParam category: String): PriceRangeByCategoryResponse {
        return coordinationService.getPriceRangeByCategory(category)
    }
}