package com.github.titainper.codi8.services.products.application

import com.github.titainper.codi8.services.brands.infrastructure.BrandRepository
import com.github.titainper.codi8.services.products.application.dto.ProductResponse
import com.github.titainper.codi8.services.products.application.dto.CreateProductRequest
import com.github.titainper.codi8.services.products.application.dto.UpdateProductRequest
import com.github.titainper.codi8.services.products.domain.Product
import com.github.titainper.codi8.services.products.domain.event.ProductCreatedEvent
import com.github.titainper.codi8.services.products.domain.event.ProductDeletedEvent
import com.github.titainper.codi8.services.products.domain.event.ProductUpdatedEvent
import com.github.titainper.codi8.services.products.infrastructure.ProductRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun list(): List<ProductResponse> {
        return productRepository.findAll().map { it.toResponse() }
    }

    fun retrieve(id: Long): ProductResponse {
        return this.findById(id).toResponse()
    }

    private fun findById(id: Long): Product {
        return productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Product not found with id: $id") }
    }

    @Transactional
    fun create(request: CreateProductRequest): ProductResponse {
        val brand = brandRepository.findById(request.brandId)
            .orElseThrow { NoSuchElementException("Brand not found with id: ${request.brandId}") }

        val product = productRepository.save(Product(
            name = request.name,
            price = request.price,
            category = request.category,
            brand = brand
        ))
        eventPublisher.publishEvent(ProductCreatedEvent(product.id))
        return product.toResponse()
    }

    @Transactional
    fun update(id: Long, request: UpdateProductRequest): ProductResponse {
        val product = this.findById(id)
        product.update(name = request.name, price = request.price, category = request.category)
        productRepository.save(product)
        eventPublisher.publishEvent(ProductUpdatedEvent(product.id))
        return product.toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        if (!productRepository.existsById(id)) {
            throw NoSuchElementException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
        eventPublisher.publishEvent(ProductDeletedEvent(id))
    }

    private fun Product.toResponse(): ProductResponse {
        return ProductResponse(
            id = id,
            name = name,
            price = price,
            category = category,
            brandId = brand.id,
            brandName = brand.name
        )
    }
}