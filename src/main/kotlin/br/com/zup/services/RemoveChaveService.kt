package br.com.zup.services

import br.com.zup.exceptions.NotFoundException
import br.com.zup.repository.ChavePixRepository
import br.com.zup.requests.RemoveChaveRequest
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class RemoveChaveService(
    @Inject private val chavePixRepository: ChavePixRepository,
    @Inject private val bcbService: BCBServiceImpl
) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun remove(@Valid request: RemoveChaveRequest) {

        val optional = this.chavePixRepository.findByValorChaveAndParticipant(request.key!!, request.participant!!)

        if (!optional.isPresent) {
            logger.error("não foi encontrado cliente com a chave ${request.key}")

            throw NotFoundException("cliente não foi encontrado")
        }

        val chavePix = optional.get()

        this.chavePixRepository.delete(chavePix)
        this.bcbService.removeChavePix(chavePix)

        logger.info("chave pix foi removida do clienteID ${chavePix.clienteId}")
    }
}