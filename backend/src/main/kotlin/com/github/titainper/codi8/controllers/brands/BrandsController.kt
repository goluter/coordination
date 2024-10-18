package com.github.titainper.codi8.controllers.brands

import com.github.titainper.codi8.services.brands.application.BrandService
import com.github.titainper.codi8.services.brands.application.dto.BrandResponse
import com.github.titainper.codi8.services.brands.application.dto.CreateBrandRequest
import com.github.titainper.codi8.services.brands.domain.Brand
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brands")
class BrandsController(private val brandService: BrandService) {

    @GetMapping
    fun list(): ResponseEntity<List<BrandResponse>> =
        ResponseEntity.ok(brandService.list())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateBrandRequest): ResponseEntity<BrandResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(brandService.create(request))

    @GetMapping("/{id}")
    fun retrieve(@PathVariable id: Long): ResponseEntity<BrandResponse> =
        ResponseEntity.ok(brandService.retrieve(id))

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody brandDetails: Brand): ResponseEntity<BrandResponse> =
        ResponseEntity.ok(brandService.update(id, brandDetails))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        brandService.delete(id)
        return ResponseEntity.noContent().build()
    }
}