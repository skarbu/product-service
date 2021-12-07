package net.skarbek.productservice.domain.product

import net.skarbek.productservice.domain.product.id.ProductIdentifier
import net.skarbek.productservice.infrastructure.product.ProductMongoPersistence
import net.skarbek.productservice.infrastructure.product.ProductPersistenceRepository
import org.springframework.core.convert.ConversionService
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductRepository(
    val productPersistenceRepository: ProductPersistenceRepository,
    val conversionService: ConversionService,
    val mongoTemplate: ReactiveMongoTemplate
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

    fun findByProductId(productIdentifier: ProductIdentifier): Flux<Product> {
        return mongoTemplate.find(
            query(where("identifiers." + productIdentifier.idType).`is`(productIdentifier.idValue)),
            ProductMongoPersistence::class.java
        )
            .mapNotNull { result -> conversionService.convert(result, Product::class.java) }


    }

    fun findByVariantId(productIdentifier: ProductIdentifier): Flux<Product> {
        return mongoTemplate.find(
            query(where("variants.ids." + productIdentifier.idType).`is`(productIdentifier.idValue)),
            ProductMongoPersistence::class.java
        )
            .mapNotNull { result -> conversionService.convert(result, Product::class.java) }

    }

}