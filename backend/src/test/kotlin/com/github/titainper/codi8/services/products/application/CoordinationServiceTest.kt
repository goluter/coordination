package com.github.titainper.codi8.services.products.application

import com.github.titainper.codi8.services.products.infrastructure.ProductRepositoryCustom
import com.github.titainper.codi8.services.products.infrastructure.dto.ProductDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CoordinationServiceTest {

    @Mock
    private lateinit var productRepositoryCustom: ProductRepositoryCustom

    private lateinit var coordinationService: CoordinationService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        coordinationService = CoordinationService(productRepositoryCustom)
    }

    @Test
    fun `getLowestPricesByBrand should return correct response`() {
        val brand = "TestBrand"
        val mockCategoryPrices = listOf(
            ProductDto("Category1", brand, 100L),
            ProductDto("Category2", brand, 200L)
        )

        `when`(productRepositoryCustom.findLowestPricesByBrand(brand)).thenReturn(mockCategoryPrices)

        val result = coordinationService.getLowestPricesByBrand(brand)

        assertEquals(brand, result.brand)
        assertEquals(2, result.categories.size)
        assertEquals(300, result.totalPrice)
    }

    @Test
    fun `getLowestPricesByCategory should return correct response for all categories`() {
        val categories = listOf("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")
        val mockPriceRanges = categories.mapIndexed { index, category ->
            val price = (index + 1) * 100L
            Pair(ProductDto(category, "Brand$index", price), ProductDto(category, "Brand${index + 8}", price * 2))
        }

        mockPriceRanges.forEach { (low, _) ->
            `when`(productRepositoryCustom.findPriceRangeByCategory(low.category)).thenReturn(Pair(low, low))
        }

        val result = coordinationService.getLowestPricesByCategory()

        assertEquals(categories.size, result.categories.size)
        categories.forEachIndexed { index, category ->
            val categoryResult = result.categories.find { it.category == category }
            assertNotNull(categoryResult)
            assertEquals("Brand$index", categoryResult?.brand)
            assertEquals((index + 1) * 100, categoryResult?.price)
        }

        val expectedTotalPrice = (1..categories.size).sum() * 100
        assertEquals(expectedTotalPrice, result.totalPrice)
    }

    @Test
    fun `getPriceRangeByCategory should return correct response`() {
        val category = "TestCategory"
        val mockPriceRange = Pair(
            ProductDto(category, "LowBrand", 100L),
            ProductDto(category, "HighBrand", 200L)
        )

        `when`(productRepositoryCustom.findPriceRangeByCategory(category)).thenReturn(mockPriceRange)

        val result = coordinationService.getPriceRangeByCategory(category)

        assertEquals(category, result.category)
        assertEquals("LowBrand", result.lowestPrice[0].brand)
        assertEquals("HighBrand", result.highestPrice[0].brand)
    }
}