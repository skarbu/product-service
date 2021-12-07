package net.skarbek.productservice.domain.product

import net.skarbek.productservice.api.response.ProductView
import net.skarbek.productservice.domain.attribute.AttributeLevel
import net.skarbek.productservice.domain.product.id.ProductIdentifier
import net.skarbek.productservice.infrastructure.product.ProductPersistenceRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class FindDrugService
constructor(
    val productRepository: ProductRepository
) {


    fun findAll(): Flux<Product> {
        return productRepository.findAll();
    }

    fun findById(productIdentifier: ProductIdentifier): Flux<Product> {
        val level = productIdentifier.idType.level
        return when (level) {
            AttributeLevel.PRODUCT -> productRepository.findByProductId(productIdentifier)
            AttributeLevel.VARIANT -> productRepository.findByVariantId(productIdentifier)
        }


    }

}
