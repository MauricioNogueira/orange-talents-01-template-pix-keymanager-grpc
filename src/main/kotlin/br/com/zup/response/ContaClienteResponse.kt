package br.com.zup.response

class ContaClienteResponse(
    val tipo: String,
    val instituicao: Instituicao,
    val agencia: String,
    val numero: String,
    val titular: Titular
) {
}

class Instituicao(val nome: String, val ispb: String) {}

class Titular(val id: String, val nome: String, val cpf: String) {

    override fun toString(): String {
        return "Titular [id: $id, nome: $nome, cpf: $cpf]"
    }
}