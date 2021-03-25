package br.com.zup.response

import br.com.zup.dto.BankAccount
import br.com.zup.dto.Owner
import java.time.LocalDateTime

class CreatePixKeyResponse(
    val keyType: String?,
    val key: String?,
    val bankAccount: BankAccount?,
    val owner: Owner?
) {
    var createdAt: LocalDateTime? = null

    override fun toString(): String {
        return "CreatePixKeyResponse(keyType=$keyType, key=$key, bankAccount=$bankAccount, owner=$owner, createdAt=$createdAt)"
    }
}