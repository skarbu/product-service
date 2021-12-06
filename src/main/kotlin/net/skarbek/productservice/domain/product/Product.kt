package net.skarbek.productservice.domain.product

import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.product.id.ProductIdentifier

class Product
constructor(
    var id: String,
    var identifiers: Set<ProductIdentifier>,
    var attributes: Map<AttributeDefinition, AttributeValue>
)