package com.github.titainper.codi8.services.brands.domain

import jakarta.persistence.*

@Entity
class Brand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var name: String
) {
    fun update(name: String? = null) {
        name?.let { this.name = it }
    }
}