package net.skarbek.productservice.batch

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.attribute.AttributeSource
import net.skarbek.productservice.domain.product.AttributeValue
import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.domain.product.ProductRepository
import net.skarbek.productservice.domain.product.id.IdType
import net.skarbek.productservice.domain.product.id.ProductIdentifier
import org.bson.types.ObjectId
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.FileReader

@Component
class CsiozImporter(
    val attributeDefinitionRepository: AttributeDefinitionRepository,
    val productRepository: ProductRepository
) {
    companion object {
        const val ID_INDEX = 0
    }

    fun doImport() {
        Thread { doImportInternal() }
            .start()

    }

    private fun doImportInternal() {
        val file = ClassPathResource("csioz.csv").file
        CSVReaderBuilder(FileReader(file))
            .withCSVParser(
                CSVParserBuilder()
                    .withSeparator(';')
                    .build()
            )
            .build()
            .use { reader ->
                val iterator = reader.iterator()
                val headers = iterator.next();
                val headerIndexesToAttributes = defineHeaderIndexes(headers)
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    processItem(next, headerIndexesToAttributes)
                }
            }
    }

    private fun processItem(item: Array<String>, headerIndexesToAttributes: Map<Int, AttributeDefinition>) {
        val attributes = headerIndexesToAttributes.map { entry ->
            val atbValue = item[entry.key]
            entry.value to AttributeValue(atbValue)
        }.toMap()


        val product = Product(
            id = ObjectId().toString(),
            identifiers = setOf(ProductIdentifier(IdType.EAN, item[ID_INDEX])),
            attributes = attributes
        )

        productRepository.save(product).block()

    }

    private fun defineHeaderIndexes(headers: Array<String>): Map<Int, AttributeDefinition> {
        val attributeDefinitions: List<AttributeDefinition> =
            attributeDefinitionRepository.getAllForSystem(AttributeSource.CSIOZ)
        return headers.mapIndexed() { index, header ->
            val atb = attributeDefinitions.firstOrNull { a -> header == a.externalName }
            Pair(index, atb)
        }
            .mapNotNull { p -> p.second?.let { Pair(p.first, it) } }
            .toMap()

    }


}


