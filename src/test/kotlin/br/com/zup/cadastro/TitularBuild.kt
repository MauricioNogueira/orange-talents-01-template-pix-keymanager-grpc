package br.com.zup.cadastro

import br.com.zup.response.Titular

class TitularBuild {
    var id: String? = ""
    var nome: String? = ""
    var cpf: String? = ""

    fun id(id: String): TitularBuild {
        this.id = id

        return this
    }

    fun nome(nome: String): TitularBuild {
        this.nome = nome

        return this
    }

    fun cpf(cpf: String): TitularBuild {
        this.cpf = cpf

        return this
    }

    fun build(): Titular {
        return Titular(this.id!!, this.nome!!, this.cpf!!)
    }
}