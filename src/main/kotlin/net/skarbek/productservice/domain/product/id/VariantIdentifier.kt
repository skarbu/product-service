package net.skarbek.productservice.domain.product.id

data class VariantIdentifier(
    val idType: IdType,
    private val rawIdValue: String
) {
    val idValue = rawIdValue.trim().replace("\\s+".toRegex(), " ")
}