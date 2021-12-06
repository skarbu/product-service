package net.skarbek.productservice.domain.product

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AddDrugService(
    var productRepository: ProductRepository
) {

    fun save(product: Product): Mono<Product> {
        return productRepository.save(product)
    }

}