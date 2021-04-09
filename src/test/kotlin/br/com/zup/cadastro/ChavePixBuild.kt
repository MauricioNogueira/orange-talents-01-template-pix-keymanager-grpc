package br.com.zup.cadastro

import br.com.zup.model.ChavePix
import java.time.LocalDateTime
import java.util.*

class ChavePixBuild {
    var id: UUID? = null
    var nameChave: String? = ""
    var valor: String? = ""
    var nameConta: String? = ""
    var identificadorCliente: String? = ""
    var ispb: String? = ""
    var agencia: String? = ""
    var numeroConta: String? = ""
    var nomeCliente: String? = ""
    var documento: String? = ""
    var createdAt: LocalDateTime? = null

    fun setId(id: UUID): ChavePixBuild {
        this.id = id
        return this
    }

    fun setTipoChave(nameChave: String): ChavePixBuild {
        this.nameChave = nameChave
        return this
    }

    fun setValorChave(valor: String): ChavePixBuild {
        this.valor = valor
        return this
    }

    fun setTipoConta(nameConta: String): ChavePixBuild {
        this.nameConta = nameConta
        return this
    }

    fun setClienteId(identificadorCliente: String): ChavePixBuild {
        this.identificadorCliente = identificadorCliente
        return this
    }

    fun setParticipant(ispb: String): ChavePixBuild {
        this.ispb = ispb
        return this
    }

    fun setBranch(agencia: String): ChavePixBuild {
        this.agencia = agencia
        return this
    }

    fun setAccountNumber(numeroConta: String): ChavePixBuild {
        this.numeroConta = numeroConta
        return this
    }

    fun setTitular(nomeCliente: String): ChavePixBuild {
        this.nomeCliente = nomeCliente
        return this
    }

    fun setCpf(documento: String): ChavePixBuild {
        this.documento = documento
        return this
    }

    fun setCreatedAt(createdAt: LocalDateTime): ChavePixBuild {
        this.createdAt = createdAt
        return this
    }

    fun build(): ChavePix {
        return ChavePix().apply {
            tipoChave =  nameChave
            valorChave = if (valor!!.isEmpty() && nameChave.equals("RANDOM")) UUID.randomUUID().toString() else valor
            tipoConta = nameConta
            clienteId = identificadorCliente
            participant = ispb
            branch = agencia
            accountNumber = numeroConta
            titular = nomeCliente
            cpf = documento
        }
    }
}