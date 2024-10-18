package com.github.titainper.codi8.services.brands.infrastructure

import com.github.titainper.codi8.services.brands.domain.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {
    fun existsByName(name: String): Boolean
}