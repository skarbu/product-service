package net.skarbek.productservice.api.converter

import net.skarbek.productservice.api.request.AddProductRequest
import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.ProductIdentifier
import org.bson.types.ObjectId
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class AddProductRequestToProduct(
    val attributeDefinitionRepository: AttributeDefinitionRepository
) : Converter<AddProductRequest, Product> {


    override fun convert(source: AddProductRequest): Product? {
        val id = ObjectId.get().toString()
        val identifier = setOf(ProductIdentifier(IdType.EAN, source.ean))
        val attributes = source.attributes
            .mapKeys { e -> attributeDefinitionRepository.getByName(e.key) }
            .mapValues { e -> AttributeValue(e.value) }
        return Product(id = id, identifiers = identifier, attributes = attributes)
    }
}