package net.skarbek.productservice.configuration

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import net.skarbek.productservice.infrastructure.product.ProductPersistenceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [ProductPersistenceRepository::class])
class MongoConfiguration  {

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb://root:Lideo123^@localhost:27017");
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(mongoClient(), "test")
    }

}