package com.github.titainper.codi8.services.brands.application

import com.github.titainper.codi8.services.brands.application.dto.CreateBrandRequest
import com.github.titainper.codi8.services.brands.domain.Brand
import com.github.titainper.codi8.services.brands.domain.event.BrandCreateEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandDeletedEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandUpdatedEvent
import com.github.titainper.codi8.services.brands.infrastructure.BrandRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.context.ApplicationEventPublisher
import java.util.*

class BrandServiceTest {

    @Mock
    private lateinit var brandRepository: BrandRepository

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    private lateinit var brandService: BrandService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        brandService = BrandService(brandRepository, eventPublisher)
    }

    @Test
    fun `list should return all brands`() {
        val brands = listOf(
            Brand(id = 1, name = "Brand1"),
            Brand(id = 2, name = "Brand2")
        )
        `when`(brandRepository.findAll()).thenReturn(brands)

        val result = brandService.list()

        assertEquals(2, result.size)
        assertEquals("Brand1", result[0].name)
        assertEquals("Brand2", result[1].name)
    }

    @Test
    fun `retrieve should return brand when found`() {
        val brand = Brand(id = 1, name = "TestBrand")
        `when`(brandRepository.findById(1)).thenReturn(Optional.of(brand))

        val result = brandService.retrieve(1)

        assertEquals(1, result.id)
        assertEquals("TestBrand", result.name)
    }

    @Test
    fun `retrieve should throw exception when brand not found`() {
        `when`(brandRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            brandService.retrieve(1)
        }
    }

    @Test
    fun `create should return new brand when name is unique`() {
        val request = CreateBrandRequest("NewBrand")
        val brand = Brand(id = 1, name = "NewBrand")
        `when`(brandRepository.existsByName("NewBrand")).thenReturn(false)
        `when`(brandRepository.save(any())).thenReturn(brand)

        val result = brandService.create(request)

        assertEquals(1, result.id)
        assertEquals("NewBrand", result.name)
    }

    @Test
    fun `create should throw exception when brand name already exists`() {
        val request = CreateBrandRequest("ExistingBrand")
        `when`(brandRepository.existsByName("ExistingBrand")).thenReturn(true)

        assertThrows(IllegalArgumentException::class.java) {
            brandService.create(request)
        }
    }

    @Test
    fun `create brand should save brand and publish event`() {
        val createRequest = CreateBrandRequest("Test Brand")
        val savedBrand = Brand(1L, "Test Brand")

        `when`(brandRepository.existsByName("Test Brand")).thenReturn(false)
        `when`(brandRepository.save(any())).thenReturn(savedBrand)

        brandService.create(createRequest)

        verify(brandRepository).save(any())
        verify(eventPublisher).publishEvent(any(BrandCreateEvent::class.java))
    }

    @Test
    fun `update should return updated brand`() {
        val brand = Brand(id = 1, name = "OldName")
        val updatedBrand = Brand(id = 1, name = "NewName")
        `when`(brandRepository.findById(1)).thenReturn(Optional.of(brand))
        `when`(brandRepository.save(any())).thenReturn(updatedBrand)

        val result = brandService.update(1, updatedBrand)

        assertEquals(1, result.id)
        assertEquals("NewName", result.name)
    }

    @Test
    fun `update brand should update brand and publish event`() {
        val brandId = 1L
        val updateParams = Brand(name = "Updated Brand")
        val existingBrand = Brand(brandId, "Old Brand")
        val updatedBrand = Brand(brandId, "Updated Brand")

        `when`(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand))
        `when`(brandRepository.save(any())).thenReturn(updatedBrand)

        brandService.update(brandId, updateParams)

        verify(brandRepository).save(any())
        verify(eventPublisher).publishEvent(any(BrandUpdatedEvent::class.java))
    }

    @Test
    fun `delete should remove brand when found`() {
        `when`(brandRepository.existsById(1)).thenReturn(true)
        doNothing().`when`(brandRepository).deleteById(1)

        assertDoesNotThrow {
            brandService.delete(1)
        }

        verify(brandRepository, times(1)).deleteById(1)
    }

    @Test
    fun `delete should throw exception when brand not found`() {
        `when`(brandRepository.existsById(1)).thenReturn(false)

        assertThrows(NoSuchElementException::class.java) {
            brandService.delete(1)
        }
    }

    @Test
    fun `delete brand should delete brand and publish event`() {
        val brandId = 1L

        `when`(brandRepository.existsById(brandId)).thenReturn(true)

        brandService.delete(brandId)

        verify(brandRepository).deleteById(brandId)
        verify(eventPublisher).publishEvent(any(BrandDeletedEvent::class.java))
    }
}