package net.skarbek.productservice.infrastructure.product.converter

import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.Variant
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.VariantIdentifier
import net.skarbek.productservice.infrastructure.product.VariantPersistence
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PersistenceToVariant(
    val attributeDefinitionRepository: AttributeDefinitionRepository
) : Converter<VariantPersistence, Variant>{

    override fun convert(source: VariantPersistence): Variant? {

        val ids = source.ids
            .map { VariantIdentifier(IdType.valueOf(it.key), it.value) }
            .toSet()
        val atbs = source.attributes
            .map { attributeDefinitionRepository.getByName(it.key) to AttributeValue(it.value) }
            .toMap()
        return Variant(ids, atbs)
    }
}