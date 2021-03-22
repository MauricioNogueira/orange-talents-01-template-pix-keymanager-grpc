package br.com.zup.services

import br.com.zup.exceptions.NotFoundException
import br.com.zup.repository.ChavePixRepository
import br.com.zup.requests.RemoveChaveRequest
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class RemoveChaveService(
    @Inject private val chavePixRepository: ChavePixRepository
) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun remove(@Valid request: RemoveChaveRequest) {
        val uuid: UUID = UUID.fromString(request.pixId)

        val optional = this.chavePixRepository.findByIdAndClienteId(uuid, request.clienteId.toString())

        if (!optional.isPresent) {
            logger.error("cliente não foi encontrado: Cliente ID: ${request.clienteId}")

            throw NotFoundException("cliente não foi encontrado")
        }

        this.chavePixRepository.delete(optional.get())
    }
}