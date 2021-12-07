package net.skarbek.productservice.infrastructure.product

import net.skarbek.productservice.batch.csioz.CsiozVariant
import net.skarbek.productservice.domain.attribute.*
import org.springframework.stereotype.Component

@Component
class AttributeDefinitionInMemoryRepository : AttributeDefinitionRepository {

    val atbs: Map<String, AttributeDefinition> = mapOf(
        "commercialName" to AttributeDefinition(
            name = "commercialName",
            externalName = "Nazwa Produktu Leczniczego",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "commonName" to AttributeDefinition(
            name = "commonName",
            externalName = "Nazwa powszechnie stosowana",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "dosage" to AttributeDefinition(
            name = "dosage",
            externalName = "Moc",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "activeSubstances" to AttributeDefinition(
            name = "activeSubstances",
            externalName = "Substancja czynna",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "form" to AttributeDefinition(
            name = "form",
            externalName = "Postać farmaceutyczna",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "atc" to AttributeDefinition(
            name = "atc",
            externalName = "Kod ATC",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "productCharacteristic" to AttributeDefinition(
            name = "productCharacteristic",
            externalName = "Charakterystyka",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "leaflet" to AttributeDefinition(
            name = "leaflet",
            externalName = "Ulotka",
            level = AttributeLevel.PRODUCT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "package" to AttributeDefinition(
            name = "package",
            externalName = CsiozVariant.CSIOZ_PACKAGE,
            level = AttributeLevel.VARIANT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),
        "fieldOfUse" to AttributeDefinition(
            name = "fieldOfUse",
            externalName = CsiozVariant.CSIOZ_FIELD_OF_USE,
            level = AttributeLevel.VARIANT,
            source = AttributeSource.CSIOZ,
            type = AttributeType.STRING,
            multiValue = false
        ),

        )

    override fun getByName(name: String): AttributeDefinition {
        return atbs[name] ?: throw IllegalArgumentException()
    }

    override fun getAllForSystem(source: AttributeSource): Set<AttributeDefinition> {
        return atbs
            .filter { entry -> entry.value.source == source }
            .map { entry -> entry.value }
            .toSet()
    }
}