package net.skarbek.productservice.batch.csioz

import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.attribute.AttributeLevel
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.ProductIdentifier

class CsiozProduct
constructor(
    private var itemRow: Array<String>,
    private var headers: CsiozHeaders,
    private var attributeDefinitions: Set<AttributeDefinition>,
) {

    companion object {
        const val ID = 0
        const val VARIANTS = 13

    }

    var productIdentifier = ProductIdentifier(IdType.CSIOZ, itemRow[ID])
    var attributes: Map<AttributeDefinition, AttributeValue> = extractAttributes(headers, itemRow)

    var variants: Set<CsiozVariant> = itemRow[VARIANTS]
        .lines()
        .mapNotNull { it.split(",") }
        .filter { it.size >= 3 }
        .map { CsiozVariant(it, attributeDefinitions) }
        .toSet()


    private fun extractAttributes(
        headers: CsiozHeaders,
        itemRow: Array<String>
    ): Map<AttributeDefinition, AttributeValue> {
        return headers.attributeIndexes
            .filter { it.value.level == AttributeLevel.PRODUCT }
            .map { entry ->
                val atbValue = itemRow[entry.key]
                entry.value to AttributeValue(atbValue)
            }
            .toMap()
    }


}