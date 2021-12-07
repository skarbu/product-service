package net.skarbek.productservice.api.converter

import net.skarbek.productservice.domain.product.id.IdType
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToIdType : Converter<String, IdType>{

    override fun convert(source: String): IdType? {
        return IdType.valueOf(source)
    }

}