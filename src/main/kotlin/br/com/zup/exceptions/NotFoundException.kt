package br.com.zup.exceptions

class NotFoundException(private val mensagem: String = "não foi encontrado"): RuntimeException(mensagem) {
}