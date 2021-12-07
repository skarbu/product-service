package net.skarbek.productservice.domain.product

import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.product.id.VariantIdentifier

class Variant(
    val ids: Set<VariantIdentifier>,
    var attributes: Map<AttributeDefinition, AttributeValue>
) {


}