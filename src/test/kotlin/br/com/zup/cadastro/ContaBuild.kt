package br.com.zup.cadastro

import br.com.zup.response.ContaClienteResponse
import br.com.zup.response.Instituicao
import br.com.zup.response.Titular

class ContaBuild {
    var tipo: String? = ""
    var instituicao: Instituicao? = null
    var agencia: String? = ""
    var numero: String? = ""
    var titular: Titular? = null

    fun setTipo(tipo: String): ContaBuild {
        this.tipo = tipo

        return this
    }

    fun setInstituicao(instituicao: Instituicao): ContaBuild {
        this.instituicao = instituicao

        return this
    }

    fun setAgencia(agencia: String): ContaBuild {
        this.agencia = agencia

        return this
    }

    fun setNumero(numero: String): ContaBuild {
        this.numero = numero

        return this
    }

    fun setTitular(titular: Titular): ContaBuild {
        this.titular = titular

        return this
    }

    fun build(): ContaClienteResponse {
        return ContaClienteResponse(tipo = this.tipo!!, instituicao = this.instituicao!!, agencia = this.agencia!!, numero = this.numero!!, titular = this.titular!!)
    }
}