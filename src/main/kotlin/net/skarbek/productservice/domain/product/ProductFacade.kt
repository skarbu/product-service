package net.skarbek.productservice.domain.product

import net.skarbek.productservice.api.request.AddProductRequest
import net.skarbek.productservice.api.response.ProductView
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductFacade
constructor(
    var addDrugService: AddDrugService,
    var findDrugService: FindDrugService,
    var conversionService: ConversionService
) {
    fun findAll(): Flux<ProductView> {
        return findDrugService.findAll()
            .mapNotNull { p -> conversionService.convert(p, ProductView::class.java); };
    }

    fun save(productView: AddProductRequest): Mono<ProductView> {
        val product = conversionService.convert(productView, Product::class.java) ?: return Mono.empty();
        return addDrugService.save(product)
            .mapNotNull { p -> conversionService.convert(p, ProductView::class.java) }
    }


}