package com.github.titainper.codi8.services.products.infrastructure

import com.github.titainper.codi8.services.products.domain.QProduct
import com.github.titainper.codi8.services.products.infrastructure.dto.ProductDto
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductRepositoryCustom {
    private val product = QProduct.product

    override fun findLowestPricesByBrand(brand: String): List<ProductDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    ProductDto::class.java,
                    product.category,
                    product.brand.name,
                    product.price.min()
                )
            )
            .from(product)
            .where(product.brand.name.eq(brand))
            .groupBy(product.category)
            .fetch()
    }

    override fun findPriceRangeByCategory(category: String): Pair<ProductDto, ProductDto> {
        val lowestPrice = JPAExpressions
            .select(product.price.min())
            .from(product)
            .where(product.category.eq(category))

        val highestPrice = JPAExpressions
            .select(product.price.max())
            .from(product)
            .where(product.category.eq(category))

        val result = queryFactory
            .select(
                Projections.constructor(
                    ProductDto::class.java,
                    product.category,
                    product.brand.name,
                    product.price
                )
            )
            .from(product)
            .where(
                product.category.eq(category)
                    .and(
                        product.price.eq(lowestPrice)
                            .or(product.price.eq(highestPrice))
                    )
            )
            .fetch()

        val lowestPriceResult = result.minByOrNull { it.price }
        val highestPriceResult = result.maxByOrNull { it.price }

        return Pair(
            lowestPriceResult ?: ProductDto("", "", 0,),
            highestPriceResult ?: ProductDto("", "",0)
        )
    }
}