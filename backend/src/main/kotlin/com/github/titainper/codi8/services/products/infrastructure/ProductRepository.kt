package com.github.titainper.codi8.services.products.infrastructure

import com.github.titainper.codi8.services.products.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {}