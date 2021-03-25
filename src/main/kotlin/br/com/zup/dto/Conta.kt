package br.com.zup.dto

import br.com.zup.enuns.Conta

class Conta(
    val instituicao: String,
    val agencia: String,
    val numero: String,
    val tipoConta: Conta
) {
    override fun toString(): String {
        return "Conta(instituicao='$instituicao', agencia='$agencia', numero='$numero', tipoConta=$tipoConta)"
    }
}