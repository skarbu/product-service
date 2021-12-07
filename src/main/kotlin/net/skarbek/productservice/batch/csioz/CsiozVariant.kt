package net.skarbek.productservice.batch.csioz

import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.attribute.AttributeLevel
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.VariantIdentifier

class CsiozVariant(
    val variantParams: List<String>,
    val attributeDefinitions: Set<AttributeDefinition>
) {

    companion object {
        const val VARIANT_EAN = 1
        const val CSIOZ_PACKAGE = "csiozPackage"
        const val CSIOZ_FIELD_OF_USE = "fieldOfUse"
        val ATB_NAME_BY_INDEX = mapOf(
            0 to CSIOZ_PACKAGE,
            1 to VARIANT_EAN,
            2 to CSIOZ_FIELD_OF_USE
        )
    }

    private var variantAttributeDefinitions = attributeDefinitions
        .filter { it.level == AttributeLevel.VARIANT }


    var variantIdentifier = toVariantId()
    var variantAttributes = toVariantAttbs()


    private fun toVariantId(): VariantIdentifier {
        return VariantIdentifier(IdType.EAN, variantParams[VARIANT_EAN])
    }


    private fun toVariantAttbs(): Map<AttributeDefinition, AttributeValue> {
        return variantParams
            .mapIndexedNotNull() { index, rawAttribute
                ->
                toVariantAtb(index, rawAttribute)
            }
            .toMap()
    }

    private fun toVariantAtb(index: Int, rawAttribute: String): Pair<AttributeDefinition, AttributeValue>? {
        val attbName = ATB_NAME_BY_INDEX[index]
        val attributeDefinition = variantAttributeDefinitions
            .find { it.externalName == attbName }
        if (attributeDefinition != null) {
            return Pair(attributeDefinition, AttributeValue(rawAttribute))
        }
        return null;
    }
}