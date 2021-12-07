package net.skarbek.productservice.domain.attribute

class AttributeDefinition(
    var name: String,
    var externalName: String,
    var level: AttributeLevel,
    var source: AttributeSource,
    var type: AttributeType,
    var multiValue: Boolean
) {


}