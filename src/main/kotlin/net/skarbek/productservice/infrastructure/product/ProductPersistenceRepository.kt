package net.skarbek.productservice.infrastructure.product

import net.skarbek.productservice.domain.product.id.IdType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux


interface ProductPersistenceRepository : ReactiveMongoRepository<ProductMongoPersistence, ObjectId> {


}