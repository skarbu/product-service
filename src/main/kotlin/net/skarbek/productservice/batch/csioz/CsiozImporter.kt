package net.skarbek.productservice.batch.csioz

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import net.skarbek.productservice.domain.attribute.AttributeDefinition
import net.skarbek.productservice.domain.attribute.AttributeDefinitionRepository
import net.skarbek.productservice.domain.attribute.AttributeSource
import net.skarbek.productservice.domain.product.Product
import net.skarbek.productservice.domain.product.ProductRepository
import net.skarbek.productservice.domain.product.Variant
import org.bson.types.ObjectId
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.FileReader
import java.time.Duration
import java.time.LocalDateTime

@Component
class CsiozImporter(
    val attributeDefinitionRepository: AttributeDefinitionRepository,
    val productRepository: ProductRepository,
) {

    var attributeDefinitions = setOf<AttributeDefinition>()

    fun doImport() {
        Thread {
            var start = LocalDateTime.now();
            refreshAttributeDefinitions()
            doImportInternal()
            var end = LocalDateTime.now();
            println(Duration.between(start, end).toSeconds())
        }
            .start()

    }

    private fun refreshAttributeDefinitions() {
        this.attributeDefinitions = attributeDefinitionRepository.getAllForSystem(AttributeSource.CSIOZ);
    }

    private fun doImportInternal() {
        val file = ClassPathResource("csioz.csv").file

        val csvReader = CSVReaderBuilder(FileReader(file))
            .withCSVParser(
                CSVParserBuilder()
                    .withSeparator(';')
                    .build()
            )
            .build()

        csvReader.use { reader ->
            val iterator = reader.iterator()
            val csiozHeaders = CsiozHeaders(iterator.next(), this.attributeDefinitions);
            while (iterator.hasNext()) {
                val next = iterator.next()

                val csiozProduct = CsiozProduct(next, csiozHeaders, attributeDefinitions)

                save(csiozProduct)

            }
        }
    }

    private fun save(csiozProduct: CsiozProduct) {
        val variants = csiozProduct.variants
            .map { Variant(setOf(it.variantIdentifier), it.variantAttributes) }
            .toSet()
        val product = Product(
            id = ObjectId().toString(),
            identifiers = setOf(csiozProduct.productIdentifier),
            attributes = csiozProduct.attributes,
            variants = variants
        )

        productRepository.save(product).block()

    }


}


