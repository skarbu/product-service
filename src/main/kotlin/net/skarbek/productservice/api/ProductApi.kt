package net.skarbek.productservice.api

import net.skarbek.productservice.api.request.AddProductRequest
import net.skarbek.productservice.api.response.ProductView
import net.skarbek.productservice.batch.csioz.CsiozImporter
import net.skarbek.productservice.domain.product.ProductFacade
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.ProductIdentifier
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductApi(
    val productFacade: ProductFacade,
    val csiozImporter: CsiozImporter
) {

    @GetMapping("/{idType}/{id}")
    fun findById(@PathVariable idType: IdType, @PathVariable id: String): Flux<ProductView> {
        return productFacade.findById(ProductIdentifier(idType, id))
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