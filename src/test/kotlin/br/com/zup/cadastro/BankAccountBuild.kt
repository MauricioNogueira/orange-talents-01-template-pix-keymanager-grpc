package br.com.zup.cadastro

import br.com.zup.dto.BankAccount

class BankAccountBuild {
    var participant: String? = ""
    var branch: String? = ""
    var accountNumber: String? = ""
    var accountType: String? = ""

    fun setParticipant(participant: String): BankAccountBuild {
        this.participant = participant
        return this
    }

    fun setBranch(branch: String): BankAccountBuild {
        this.branch = branch
        return this
    }

    fun setAccountNumber(accountNumber: String): BankAccountBuild {
        this.accountNumber = accountNumber
        return this
    }

    fun setAccountType(accountType: String): BankAccountBuild {
        this.accountType = accountType
        return this
    }

    fun build(): BankAccount {
        return BankAccount(participant = participant, branch = branch, accountNumber = accountNumber, accountType = accountType)
    }
}