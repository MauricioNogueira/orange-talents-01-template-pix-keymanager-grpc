package br.com.zup.cadastro

import br.com.zup.NovaChavePixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.requests.NovaChaveRequest

class CriaRequestNovaChavePixBuild {
    var tipoChave: TipoChave? = TipoChave.UNKNOWN_TYPE
    var valorChave: String? = ""
    var tipoConta: TipoConta? = TipoConta.UNKNOWN_ACCOUNT
    var clienteId: String? = ""

    fun tipoChave(tipoChave: TipoChave): CriaRequestNovaChavePixBuild {
        this.tipoChave = tipoChave

        return this
    }

    fun valorChave(valorChave: String): CriaRequestNovaChavePixBuild {
        this.valorChave = valorChave

        return this
    }

    fun tipoConta(tipoConta: TipoConta): CriaRequestNovaChavePixBuild {
        this.tipoConta = tipoConta
        return this
    }

    fun clienteId(clienteId: String): CriaRequestNovaChavePixBuild {
        this.clienteId = clienteId
        return this
    }

    fun build(): NovaChavePixRequest {
        return NovaChavePixRequest.newBuilder()
            .setIdentificadorCliente(this.clienteId)
            .setTipoChave(this.tipoChave)
            .setValorChave(this.valorChave)
            .setTipoConta(this.tipoConta)
            .build()
    }

    fun toRequest(): NovaChaveRequest {
        return NovaChaveRequest(this.clienteId!!, this.tipoChave!!, this.valorChave, this.tipoConta!!)
    }

    override fun toString(): String {
        return "CriaRequestNovaChavePix(tipoChave=$tipoChave, valorChave=$valorChave, tipoConta=$tipoConta, clienteId=$clienteId)"
    }
}