package net.skarbek.productservice.domain.attribute


interface AttributeDefinitionRepository {
    fun getByName(name: String): AttributeDefinition
    fun getAllForSystem(csioz: AttributeSource): List<AttributeDefinition>

}