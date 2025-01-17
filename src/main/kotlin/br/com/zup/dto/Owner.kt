package br.com.zup.dto

data class Owner(
    val type: String,
    val name: String,
    val taxIdNumber: String
) {
    override fun toString(): String {
        return "Owner(type='$type', name='$name', taxIdNumber='$taxIdNumber')"
    }
}