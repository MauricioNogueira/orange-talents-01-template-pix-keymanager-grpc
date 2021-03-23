package br.com.zup.requests

import br.com.zup.dto.BankAccount
import br.com.zup.dto.Owner

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