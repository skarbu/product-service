package net.skarbek.productservice.batch.csioz

import net.skarbek.productservice.domain.attribute.AttributeDefinition

class CsiozHeaders(
    private var header: Array<String>,
    private var definitions: Set<AttributeDefinition>
) {

    var attributeIndexes: Map<Int, AttributeDefinition> = extractAttributes(header, definitions)

    private fun extractAttributes(
        header: Array<String>,
        definitions: Set<AttributeDefinition>
    ): Map<Int, AttributeDefinition> {
        return header.mapIndexed() { index, header ->
            val atb = definitions.firstOrNull { a -> header == a.externalName }
            Pair(index, atb)
        }
            .mapNotNull { p -> p.second?.let { Pair(p.first, it) } }
            .toMap()
    }


}