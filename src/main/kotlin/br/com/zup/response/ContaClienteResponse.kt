package br.com.zup.response

class ContaClienteResponse(
    val tipo: String,
    val instituicao: Instituicao,
    val agencia: String,
    val numero: String,
    val titular: Titular
) {
    override fun toString(): String {
        return "ContaClienteResponse(tipo='$tipo', instituicao=$instituicao, agencia='$agencia', numero='$numero', titular=$titular)"
    }
}

class Instituicao(val nome: String, val ispb: String) {
    override fun toString(): String {
        return "Instituicao(nome='$nome', ispb='$ispb')"
    }
}

class Titular(val id: String, val nome: String, val cpf: String) {

    override fun toString(): String {
        return "Titular [id: $id, nome: $nome, cpf: $cpf]"
    }
}