package net.skarbek.productservice.domain.attribute

class AttributeDefinition(
    var name: String,
    var externalName: String,
    var source: AttributeSource,
    var type: AttributeType,
    var multiValue: Boolean
) {


}