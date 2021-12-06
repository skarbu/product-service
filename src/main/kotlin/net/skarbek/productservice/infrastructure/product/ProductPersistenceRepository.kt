package net.skarbek.productservice.infrastructure.product

import net.skarbek.productservice.domain.product.Product
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository


interface ProductPersistenceRepository : ReactiveMongoRepository<ProductMongoPersistence, ObjectId> {

}