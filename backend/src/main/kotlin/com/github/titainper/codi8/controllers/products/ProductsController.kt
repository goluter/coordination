package com.github.titainper.codi8.controllers.v1

import com.github.titainper.codi8.services.products.application.ProductService
import com.github.titainper.codi8.services.products.application.dto.ProductResponse
import com.github.titainper.codi8.services.products.application.dto.CreateProductRequest
import com.github.titainper.codi8.services.products.application.dto.UpdateProductRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductsController(private val productService: ProductService) {
    @GetMapping
    fun list(): ResponseEntity<List<ProductResponse>> =
        ResponseEntity.ok(productService.list())

    @PostMapping
    fun create(@RequestBody request: CreateProductRequest): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.create(request))

    @GetMapping("/{id}")
    fun retrieve(@PathVariable id: Long): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.retrieve(id))

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody productDetails: UpdateProductRequest): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.update(id, productDetails))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}