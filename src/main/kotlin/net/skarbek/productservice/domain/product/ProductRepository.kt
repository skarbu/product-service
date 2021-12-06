package net.skarbek.productservice.domain.product

import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.infrastructure.product.ProductMongoPersistence
import net.skarbek.productservice.infrastructure.product.ProductPersistenceRepository
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class ProductRepository(
    val productPersistenceRepository: ProductPersistenceRepository,
    val conversionService: ConversionService
) {
    fun save(product: Product): Mono<Product> {
        val productMongoPersistence =
            conversionService.convert(product, ProductMongoPersistence::class.java) ?: return Mono.error(
                IllegalArgumentException()
            )
        return productPersistenceRepository.save(productMongoPersistence)
            .mapNotNull { result -> conversionService.convert(result, Product::class.java) }
        //TODO save in solr
    }

    fun findAll(): Flux<Product> {
        return productPersistenceRepository.findAll()
            .mapNotNull { result -> conversionService.convert(result, Product::class.java) }
    }

    fun findById(ean: IdType, s: String): Optional<Product> {
        TODO("Not yet implemented")
    }

}