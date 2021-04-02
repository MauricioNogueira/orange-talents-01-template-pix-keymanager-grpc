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
    @Inject private val chavePixRepository: ChavePixRepository,
    @Inject private val bcbService: BCBService
) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun remove(@Valid request: RemoveChaveRequest) {
        val uuid = UUID.fromString(request.pixId)

        val optional = this.chavePixRepository.findByIdAndClienteId(uuid, request.clienteId!!)

        if (!optional.isPresent) {
            logger.error("não foi encontrado cliente com ID ${request.clienteId}")

            throw NotFoundException("não foi encontrado chave pix do cliente solicitado")
        }

        val chavePix = optional.get()

        this.chavePixRepository.delete(chavePix)
        this.bcbService.removeChavePix(chavePix)

        logger.info("chave pix foi removida do clienteID ${chavePix.clienteId}")
    }
}