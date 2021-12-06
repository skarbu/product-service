package net.skarbek.productservice.infrastructure.product

import org.bson.types.ObjectId
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
@TypeAlias("product")
data class ProductMongoPersistence(
    var id: ObjectId,
    var identifiers: Map<String, String>,
    var attributes: Map<String, String>
)