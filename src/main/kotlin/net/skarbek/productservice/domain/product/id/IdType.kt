package net.skarbek.productservice.domain.product.id

import net.skarbek.productservice.domain.attribute.AttributeLevel

enum class IdType(var level: AttributeLevel) {

    INTERNAL(AttributeLevel.PRODUCT),
    CSIOZ(AttributeLevel.PRODUCT),
    EAN(AttributeLevel.VARIANT),



}