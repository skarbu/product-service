package net.skarbek.productservice.api.request

data class AddProductRequest(
    var id: String?,
    var ean: String,
    var attributes: Map<String, String>
) {

}