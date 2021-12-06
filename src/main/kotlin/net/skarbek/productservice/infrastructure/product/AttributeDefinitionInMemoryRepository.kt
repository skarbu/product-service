package net.skarbek.productservice.infrastructure.product

import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.attribute.AttributeSource
import net.skarbek.productservice.domain.attribute.AttributeType
import org.springframework.stereotype.Component

@Component
class AttributeDefinitionInMemoryRepository : AttributeDefinitionRepository {

    val atbs: Map<String, AttributeDefinition> = mapOf(
        "drugName" to AttributeDefinition(
            name = "drugName",
            externalName = "Nazwa Produktu Leczniczego",
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "dosage" to AttributeDefinition(
            name = "dosage",
            externalName = "Moc",
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "package" to AttributeDefinition(
            name = "package",
            externalName = "Opakowanie",
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        )
    )

    override fun getByName(name: String): AttributeDefinition {
        return atbs[name] ?: throw IllegalArgumentException()
    }

    override fun getAllForSystem(source: AttributeSource): List<AttributeDefinition> {
        return atbs
            .filter { entry -> entry.value.source == source }
            .map { entry -> entry.value }
    }
}