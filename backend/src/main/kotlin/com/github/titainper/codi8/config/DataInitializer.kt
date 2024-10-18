package com.github.titainper.codi8.config

import com.github.titainper.codi8.services.brands.domain.Brand
import com.github.titainper.codi8.services.brands.infrastructure.BrandRepository
import com.github.titainper.codi8.services.products.domain.Product
import com.github.titainper.codi8.services.products.infrastructure.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {
    @Bean
    fun databaseInitializer(
        brandRepository: BrandRepository,
        productRepository: ProductRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            val brandPrices = mapOf(
                "A" to mapOf(
                    "상의" to listOf(11200, 12000, 13000),
                    "아우터" to listOf(5500, 57000, 59000),
                    "바지" to listOf(4200, 44000, 46000),
                    "스니커즈" to listOf(9000, 92000, 94000),
                    "가방" to listOf(2000, 22000, 24000),
                    "모자" to listOf(1700, 18000, 19000),
                    "양말" to listOf(1800, 19000, 20000),
                    "액세서리" to listOf(2300, 24000, 25000)
                ),
                "B" to mapOf(
                    "상의" to listOf(10500, 11000, 11500),
                    "아우터" to listOf(5900, 61000, 63000),
                    "바지" to listOf(3800, 40000, 42000),
                    "스니커즈" to listOf(9100, 93000, 95000),
                    "가방" to listOf(2100, 23000, 25000),
                    "모자" to listOf(2000, 21000, 22000),
                    "양말" to listOf(2000, 21000, 22000),
                    "액세서리" to listOf(2200, 23000, 24000)
                ),
                "C" to mapOf(
                    "상의" to listOf(10000, 10500, 11000),
                    "아우터" to listOf(6200, 64000, 66000),
                    "바지" to listOf(3300, 35000, 37000),
                    "스니커즈" to listOf(9200, 94000, 96000),
                    "가방" to listOf(2200, 24000, 26000),
                    "모자" to listOf(1900, 20000, 21000),
                    "양말" to listOf(2200, 23000, 24000),
                    "액세서리" to listOf(2100, 22000, 23000)
                ),
                "D" to mapOf(
                    "상의" to listOf(10100, 10600, 11100),
                    "아우터" to listOf(5100, 53000, 55000),
                    "바지" to listOf(3000, 32000, 34000),
                    "스니커즈" to listOf(9500, 97000, 99000),
                    "가방" to listOf(2500, 27000, 29000),
                    "모자" to listOf(1500, 16000, 17000),
                    "양말" to listOf(2400, 25000, 26000),
                    "액세서리" to listOf(2000, 21000, 22000)
                ),
                "E" to mapOf(
                    "상의" to listOf(10700, 11200, 11700),
                    "아우터" to listOf(5000, 52000, 54000),
                    "바지" to listOf(3800, 40000, 42000),
                    "스니커즈" to listOf(9900, 101000, 103000),
                    "가방" to listOf(2300, 25000, 27000),
                    "모자" to listOf(1800, 19000, 20000),
                    "양말" to listOf(2100, 22000, 23000),
                    "액세서리" to listOf(2100, 22000, 23000)
                ),
                "F" to mapOf(
                    "상의" to listOf(11200, 11700, 12200),
                    "아우터" to listOf(7200, 74000, 76000),
                    "바지" to listOf(4000, 42000, 44000),
                    "스니커즈" to listOf(9300, 95000, 97000),
                    "가방" to listOf(2100, 23000, 25000),
                    "모자" to listOf(1600, 17000, 18000),
                    "양말" to listOf(2300, 24000, 25000),
                    "액세서리" to listOf(1900, 20000, 21000)
                ),
                "G" to mapOf(
                    "상의" to listOf(10500, 11000, 11500),
                    "아우터" to listOf(5800, 60000, 62000),
                    "바지" to listOf(3900, 41000, 43000),
                    "스니커즈" to listOf(9000, 92000, 94000),
                    "가방" to listOf(2200, 24000, 26000),
                    "모자" to listOf(1700, 18000, 19000),
                    "양말" to listOf(2100, 22000, 23000),
                    "액세서리" to listOf(2000, 21000, 22000)
                ),
                "H" to mapOf(
                    "상의" to listOf(10800, 11300, 11800),
                    "아우터" to listOf(6300, 65000, 67000),
                    "바지" to listOf(3100, 33000, 35000),
                    "스니커즈" to listOf(9700, 99000, 101000),
                    "가방" to listOf(2100, 23000, 25000),
                    "모자" to listOf(1600, 17000, 18000),
                    "양말" to listOf(2000, 21000, 22000),
                    "액세서리" to listOf(2000, 21000, 22000)
                ),
                "I" to mapOf(
                    "상의" to listOf(11400, 11900, 12400),
                    "아우터" to listOf(6700, 69000, 71000),
                    "바지" to listOf(3200, 34000, 36000),
                    "스니커즈" to listOf(9500, 97000, 99000),
                    "가방" to listOf(2400, 26000, 28000),
                    "모자" to listOf(1700, 18000, 19000),
                    "양말" to listOf(1700, 18000, 19000),
                    "액세서리" to listOf(2400, 25000, 26000)
                )
            )

            val productNameTemplates = mapOf(
                "상의" to listOf("베이직 티셔츠", "스트라이프 셔츠", "폴로 셔츠"),
                "아우터" to listOf("데님 자켓", "가죽 재킷", "바람막이"),
                "바지" to listOf("슬림핏 진", "치노 팬츠", "조거 팬츠"),
                "스니커즈" to listOf("러닝화", "캔버스 슈즈", "하이탑 스니커즈"),
                "가방" to listOf("백팩", "토트백", "크로스백"),
                "모자" to listOf("볼캡", "버킷햇", "비니"),
                "양말" to listOf("앵클 삭스", "크루 삭스", "니 삭스"),
                "액세서리" to listOf("가죽 벨트", "손목 시계", "선글라스")
            )

            brandPrices.forEach { (brandName, categoryPrices) ->
                val brand = brandRepository.save(Brand(name = brandName))

                categoryPrices.forEach { (category, prices) ->
                    val productNames = productNameTemplates[category] ?: listOf("기본 제품")

                    prices.forEachIndexed { index, price ->
                        val productName = "${brand.name} ${productNames[index]}"

                        val product = Product(
                            name = productName,
                            price = price.toLong(),
                            category = category,
                            brand = brand
                        )
                        productRepository.save(product)
                    }
                }
            }
        }
    }
}