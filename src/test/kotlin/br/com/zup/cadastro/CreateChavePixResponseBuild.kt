package br.com.zup.cadastro

import br.com.zup.dto.BankAccount
import br.com.zup.dto.Owner
import br.com.zup.response.CreatePixKeyResponse
import java.time.LocalDateTime

class CreateChavePixResponseBuild {
    var keyType: String? = ""
    var key: String? = ""
    var bankAccount: BankAccount? = null
    var owner: Owner? = null
    var createdAt: LocalDateTime? = null

    fun setKeyType(keyType: String): CreateChavePixResponseBuild {
        this.keyType = keyType
        return this
    }

    fun setKey(key: String): CreateChavePixResponseBuild {
        this.key = key
        return this
    }

    fun setBankAccount(bankAccount: BankAccount): CreateChavePixResponseBuild {
        this.bankAccount = bankAccount
        return this
    }

    fun setOwner(onwer: Owner): CreateChavePixResponseBuild {
        this.owner = onwer
        return this
    }

    fun setCreatedAt(createdAt: LocalDateTime): CreateChavePixResponseBuild {
        this.createdAt = createdAt
        return this
    }

    fun build(): CreatePixKeyResponse {
        return CreatePixKeyResponse(keyType = keyType!!, key = key!!, bankAccount = bankAccount!!, owner = owner!!)
    }
}