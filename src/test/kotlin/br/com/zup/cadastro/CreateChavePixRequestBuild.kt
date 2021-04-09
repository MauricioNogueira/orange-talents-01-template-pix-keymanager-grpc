package br.com.zup.cadastro

import br.com.zup.dto.BankAccount
import br.com.zup.dto.Owner
import br.com.zup.requests.CreatePixKeyRequest
import java.time.LocalDateTime

class CreateChavePixRequestBuild {
    var keyType: String? = ""
    var key: String? = ""
    var bankAccount: BankAccount? = null
    var owner: Owner? = null
    var createdAt: LocalDateTime? = null

    fun setKeyType(keyType: String): CreateChavePixRequestBuild {
        this.keyType = keyType
        return this
    }

    fun setKey(key: String): CreateChavePixRequestBuild {
        this.key = key
        return this
    }

    fun setBankAccount(bankAccount: BankAccount): CreateChavePixRequestBuild {
        this.bankAccount = bankAccount
        return this
    }

    fun setOwner(onwer: Owner): CreateChavePixRequestBuild {
        this.owner = onwer
        return this
    }

    fun setCreatedAt(createdAt: LocalDateTime): CreateChavePixRequestBuild {
        this.createdAt = createdAt
        return this
    }

    fun build(): CreatePixKeyRequest {
        return CreatePixKeyRequest(keyType = keyType!!, key = key!!, bankAccount = bankAccount!!, owner = owner!!)
    }
}