package br.com.zup.dto

import java.time.LocalDateTime

class ChavePixInfo {
    var pixId: String = ""
    var clienteId: String = ""
    var tipoChave: String? = ""
    var valorChave: String? = ""
    var titular: String? = ""
    var cpf: String? = ""
    var conta: Conta? = null
    var criado: LocalDateTime? = null

    override fun toString(): String {
        return "ChavePixInfo(pixId='$pixId', clienteId='$clienteId', tipoChave='$tipoChave', valorChave='$valorChave', titular='$titular', cpf='$cpf', conta=$conta, criado=$criado)"
    }
}