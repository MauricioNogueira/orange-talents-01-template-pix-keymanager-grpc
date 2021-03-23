package br.com.zup.response

import br.com.zup.dto.BankAccount
import br.com.zup.dto.Owner

class CreatePixKeyResponse(
    val keyType: String,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
) {
    override fun toString(): String {
        return "CreatePixKeyResponse(keyType='$keyType', key='$key', bankAccount=$bankAccount, owner=$owner)"
    }
}