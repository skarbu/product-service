package net.skarbek.productservice.domain.product

import net.skarbek.productservice.infrastructure.product.ProductPersistenceRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class FindDrugService
constructor(
    val productRepository: ProductRepository
) {


    fun findAll(): Flux<Product> {
        return productRepository.findAll();
    }

}
