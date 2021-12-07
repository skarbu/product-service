package net.skarbek.productservice.domain.product

class AttributeValue(
     private var rawValue: String
) {

    val atbValue = rawValue.trim().replace("\\s+".toRegex(), " ");


}