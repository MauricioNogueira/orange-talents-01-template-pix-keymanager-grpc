package br.com.zup.services

import br.com.zup.response.ContaClienteResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.url}")
interface ItauService {

    @Get(value = "/api/v1/clientes/{clienteId}/contas")
    fun buscaCliente(@PathVariable clienteId: String, @QueryValue tipo: String): ContaClienteResponse?
}