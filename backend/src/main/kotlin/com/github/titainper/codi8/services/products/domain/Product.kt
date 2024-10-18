package com.github.titainper.codi8.services.products.domain

import com.github.titainper.codi8.services.brands.domain.Brand
import jakarta.persistence.*

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: Long,

    @Column(nullable = false)
    var category: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    var brand: Brand
) {
    // NOTE: brand 변경하는 경우는 없을 거 같은데?
    fun update(name: String? = null, price: Long? = null, category: String? = null) {
        name?.let { this.name = it }
        price?.let { this.price = it }
        category?.let { this.category = it }
    }
}