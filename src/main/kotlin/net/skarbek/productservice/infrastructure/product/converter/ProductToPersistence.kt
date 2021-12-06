package net.skarbek.productservice.infrastructure.product.converter

import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.infrastructure.product.ProductMongoPersistence
import org.bson.types.ObjectId
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProductToPersistence : Converter<Product, ProductMongoPersistence> {

    override fun convert(source: Product): ProductMongoPersistence? {
        val rawIdentifier = source.identifiers.associate { it.idType.name to it.idValue };
        val rawAttributes = source.attributes
            .mapKeys { e -> e.key.name }
            .mapValues { e -> e.value.rawValue }
        return ProductMongoPersistence(ObjectId(), rawIdentifier, rawAttributes)
    }
}