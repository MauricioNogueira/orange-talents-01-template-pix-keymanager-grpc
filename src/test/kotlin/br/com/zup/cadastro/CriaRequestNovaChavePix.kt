package br.com.zup.cadastro

import br.com.zup.NovaChavePixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.requests.NovaChaveRequest

class CriaRequestNovaChavePix {
    var tipoChave: TipoChave? = null
    var valorChave: String? = null
    var tipoConta: TipoConta? = null
    var clienteId: String? = null

    fun tipoChave(tipoChave: TipoChave): CriaRequestNovaChavePix {
        this.tipoChave = tipoChave

        return this
    }

    fun valorChave(valorChave: String): CriaRequestNovaChavePix {
        this.valorChave = valorChave

        return this
    }

    fun tipoConta(tipoConta: TipoConta): CriaRequestNovaChavePix {
        this.tipoConta = tipoConta
        return this
    }

    fun clienteId(clienteId: String): CriaRequestNovaChavePix {
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
}