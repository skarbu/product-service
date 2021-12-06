package net.skarbek.productservice.api.response

data class ProductView(
    val id: String,
    val attributes: Map<String, String>
)