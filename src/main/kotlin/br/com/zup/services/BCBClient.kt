package br.com.zup.services

import io.micronaut.http.client.annotation.Client
import br.com.zup.requests.CreatePixKeyRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import br.com.zup.requests.DeletePixKeyRequest
import io.micronaut.http.annotation.PathVariable

@Client("\${bcb.url}")
interface BCBClient {

    @Post(value = "/api/v1/pix/keys", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun registarChavePix(@Body createPixKeyRequest: CreatePixKeyRequest): String

    @Delete(value = "/api/v1/pix/keys/{key}", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun removeChavePix(@Body deletePixKeyRequest: DeletePixKeyRequest, @PathVariable("key") key: String)
}