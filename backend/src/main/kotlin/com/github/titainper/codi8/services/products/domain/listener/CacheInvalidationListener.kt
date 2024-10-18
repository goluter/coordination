package com.github.titainper.codi8.services.products.domain.listener

import com.github.titainper.codi8.services.brands.domain.event.BrandCreateEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandUpdatedEvent
import com.github.titainper.codi8.services.brands.domain.event.BrandDeletedEvent
import com.github.titainper.codi8.services.products.domain.event.ProductCreatedEvent
import com.github.titainper.codi8.services.products.domain.event.ProductUpdatedEvent
import com.github.titainper.codi8.services.products.domain.event.ProductDeletedEvent
import org.springframework.cache.CacheManager
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CacheInvalidationListener(private val cacheManager: CacheManager) {

    @EventListener
    fun handleBrandCreateEvent(event: BrandCreateEvent) {
        this.resetCache();
    }

    @EventListener
    fun handleBrandUpdatedEvent(event: BrandUpdatedEvent) {
        this.resetCache();
    }

    @EventListener
    fun handleBrandDeletedEvent(event: BrandDeletedEvent) {
        this.resetCache();
    }

    @EventListener
    fun handleProductCreatedEvent(event: ProductCreatedEvent) {
        this.resetCache();
    }

    @EventListener
    fun handleProductUpdatedEvent(event: ProductUpdatedEvent) {
        this.resetCache();
    }

    @EventListener
    fun handleProductDeletedEvent(event: ProductDeletedEvent) {
        this.resetCache();
    }

    private fun resetCache() {
        cacheManager.getCache("lowestPricesByBrand")?.clear()
        cacheManager.getCache("lowestPricesByCategory")?.clear()
        cacheManager.getCache("priceRangeByCategory")?.clear()
    }
}