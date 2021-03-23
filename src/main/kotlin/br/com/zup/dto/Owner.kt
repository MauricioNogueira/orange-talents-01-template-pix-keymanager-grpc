package br.com.zup.dto

class Owner(
    val type: String,
    val name: String,
    val taxIdNumber: String
) {
    override fun toString(): String {
        return "Owner(type='$type', name='$name', taxIdNumber='$taxIdNumber')"
    }
}