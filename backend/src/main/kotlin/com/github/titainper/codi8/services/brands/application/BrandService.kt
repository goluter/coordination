package com.github.titainper.codi8.services.brands.application

import com.github.titainper.codi8.services.brands.application.dto.BrandResponse
import com.github.titainper.codi8.services.brands.application.dto.CreateBrandRequest
import com.github.titainper.codi8.services.brands.domain.Brand
import com.github.titainper.codi8.services.brands.domain.event.BrandCreateEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandDeletedEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandUpdatedEvent
import com.github.titainper.codi8.services.brands.infrastructure.BrandRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun list(): List<BrandResponse> {
        return brandRepository.findAll().map { it.toResponse() }
    }

    fun retrieve(id: Long): BrandResponse {
        return this.findById(id).toResponse()
    }

    private fun findById(id: Long): Brand {
        return brandRepository.findById(id)
            .orElseThrow { NoSuchElementException("Brand not found with id: $id") }
    }

    @Transactional
    fun create(request: CreateBrandRequest): BrandResponse {
        if (brandRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Brand already exists with name: ${request.name}")
        }

        val brand = brandRepository.save(Brand(name = request.name))
        eventPublisher.publishEvent(BrandCreateEvent(brand.id))
        return brand.toResponse()
    }

    @Transactional
    fun update(id: Long, params: Brand): BrandResponse {
        val brand = this.findById(id)
        brand.update(params.name)
        brandRepository.save(brand)
        eventPublisher.publishEvent(BrandUpdatedEvent(brand.id))
        return brand.toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        if (!brandRepository.existsById(id)) {
            throw NoSuchElementException("Brand not found with id: $id")
        }

        brandRepository.deleteById(id)
        eventPublisher.publishEvent(BrandDeletedEvent(id))
    }

    private fun Brand.toResponse() = BrandResponse(id, name)
}