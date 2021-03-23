package br.com.zup.requests

class CreatePixKeyRequest(
    val keyType: String,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
) {
    override fun toString(): String {
        return "CreatePixKeyRequest(keyType='$keyType', key='$key', bankAccount=$bankAccount, owner=$owner)"
    }
}

class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: String
) {
    override fun toString(): String {
        return "BankAccount(participant='$participant', branch='$branch', accountNumber='$accountNumber', accountType='$accountType')"
    }
}

class Owner(
    val type: String,
    val name: String,
    val taxIdNumber: String
) {
    override fun toString(): String {
        return "Owner(type='$type', name='$name', taxIdNumber='$taxIdNumber')"
    }
}