package br.com.zup.cadastro

import br.com.zup.response.Instituicao

class InstituicaoBuild {
    var nome: String? = ""
    var ispb: String? = ""

    fun nome(nome: String): InstituicaoBuild {
        this.nome = nome

        return this
    }

    fun ispb(ispb: String): InstituicaoBuild {
        this.ispb = ispb

        return this
    }

    fun build(): Instituicao {
        return Instituicao(this.nome!!, this.ispb!!)
    }
}