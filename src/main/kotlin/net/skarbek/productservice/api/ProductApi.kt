package net.skarbek.productservice.api

import net.skarbek.productservice.api.request.AddProductRequest
import net.skarbek.productservice.api.response.ProductView
import net.skarbek.productservice.batch.CsiozImporter
import net.skarbek.productservice.domain.product.ProductFacade
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductApi(
    val productFacade: ProductFacade,
    val csiozImporter: CsiozImporter
) {

    @GetMapping
    fun findAll(): Flux<ProductView> {
        return productFacade.findAll()
    }

    @PostMapping
    fun saveProduct(@RequestBody addDrugRequest: AddProductRequest): Mono<ProductView> {
        return productFacade.save(addDrugRequest)
    }

    @PostMapping("/import")
    fun doImport(@RequestBody addDrugRequest: AddProductRequest) {
        return  csiozImporter.doImport();
    }

}