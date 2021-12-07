package net.skarbek.productservice.infrastructure.product.converter

import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.infrastructure.product.ProductMongoPersistence
import net.skarbek.productservice.infrastructure.product.VariantPersistence
import org.bson.types.ObjectId
import org.springframework.context.annotation.Lazy
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProductToPersistence(
    @Lazy val conversionService: ConversionService
) : Converter<Product, ProductMongoPersistence> {

    override fun convert(source: Product): ProductMongoPersistence? {
        val rawIdentifier = source.identifiers.associate { it.idType.name to it.idValue };
        val rawAttributes = source.attributes
            .mapKeys { e -> e.key.name }
            .mapValues { e -> e.value.atbValue }
        val rawVariants = source.variants
            .mapNotNull{ conversionService.convert(it, VariantPersistence::class.java) }
        return ProductMongoPersistence(ObjectId(), rawIdentifier, rawAttributes, rawVariants)
    }
}