package net.skarbek.productservice.api.converter

import net.skarbek.productservice.api.response.ProductView
import net.skarbek.productservice.domain.product.Product
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProductToView : Converter<Product, ProductView> {

    override fun convert(source: Product): ProductView? {
        val flatMap = source.attributes
            .mapValues { e -> e.value.rawValue }
            .mapKeys { e -> e.key.name }
        return ProductView(source.id, flatMap);
    }
}