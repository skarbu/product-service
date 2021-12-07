package net.skarbek.productservice.infrastructure.product.converter

import net.skarbek.productservice.domain.product.Variant
import net.skarbek.productservice.infrastructure.product.VariantPersistence
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class VariantToPersistence : Converter<Variant, VariantPersistence> {

    override fun convert(source: Variant): VariantPersistence? {
        val ids = source.ids.associate { it.idType.name to it.idValue }
        val atbs = source.attributes
            .map { it.key.name to it.value.atbValue }
            .toMap()

        return VariantPersistence(ids, atbs)
    }
}