package net.skarbek.productservice.infrastructure.product.converter

import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.ProductIdentifier
import net.skarbek.productservice.infrastructure.product.ProductMongoPersistence
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PersistenceToProduct(
    val attributeDefinitionRepository: AttributeDefinitionRepository
) : Converter<ProductMongoPersistence?, Product?> {


    override fun convert(source: ProductMongoPersistence): Product? {
        val id = source.id.toString()
        val identifiers = source.identifiers
            .map { e -> ProductIdentifier(IdType.valueOf(e.key), e.value) }
            .toSet()
        val atbs = source.attributes
            .mapKeys { e -> attributeDefinitionRepository.getByName(e.key) }
            .mapValues { e -> AttributeValue(e.value) }
        return Product(id = id, identifiers = identifiers, attributes = atbs)
    }



}