package br.com.zup.services

import io.micronaut.http.client.annotation.Client
import br.com.zup.requests.CreatePixKeyRequest
import io.micronaut.http.MediaType
import br.com.zup.requests.DeletePixKeyRequest
import br.com.zup.response.CreatePixKeyResponse
import io.micronaut.http.annotation.*

@Client("\${bcb.url}")
interface BCBService {

    @Post(value = "/api/v1/pix/keys", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun registarChavePix(@Body createPixKeyRequest: CreatePixKeyRequest): CreatePixKeyResponse?

    @Delete(value = "/api/v1/pix/keys/{key}", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun removeChavePix(@Body deletePixKeyRequest: DeletePixKeyRequest, @PathVariable("key") key: String): String

    @Get(value = "/api/v1/pix/keys/{key}", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun consularPix(@PathVariable("key") key: String): CreatePixKeyResponse?
}