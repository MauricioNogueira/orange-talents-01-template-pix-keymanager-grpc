package br.com.zup.services

import br.com.zup.enuns.Conta
import br.com.zup.exceptions.DataRegisterException
import br.com.zup.requests.NovaChaveRequest
import br.com.zup.repository.ChavePixRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid
import br.com.zup.model.ChavePix
import br.com.zup.enuns.TipoChave
import org.slf4j.Logger
import java.util.*
import javax.transaction.Transactional

@Validated
@Singleton
class NovaChaveService(
    @Inject private val itauService: ItauClient,
    @Inject private val chavePixRepository: ChavePixRepository,
    @Inject private val bcbService: BCBService
    ) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun registrar(@Valid novaChave: NovaChaveRequest): ChavePix {

        val contaCliente = this.itauService.buscaCliente(novaChave.idenficadorCliente, Conta.valueOf(novaChave.tipoConta.name).descricao()) ?: throw IllegalArgumentException("cliente não foi encontrado")

        val optional: Optional<ChavePix> = this.chavePixRepository.findByClienteIdAndTipoContaAndTipoChave(
            contaCliente.titular.id,
            novaChave.tipoConta.name,
            novaChave.tipoChave.name
        )

        if (optional.isPresent) {
            logger.error("chave pix já foi cadastrada: ${optional.get()}")

            throw DataRegisterException("${novaChave.tipoChave.name} já foi usado para cadastro")
        }

        var chavePix: ChavePix = novaChave.toModel(contaCliente)

        val response = this.bcbService.cadastrarChavePix(contaCliente, chavePix)

        chavePix.valorChave = response!!.key

        chavePix.apply {
            createdAt = response.createdAt
        }

        TipoChave.valueOf(novaChave.tipoChave.name).validate(chavePix.valorChave!!)

        chavePix = this.chavePixRepository.save(chavePix)

        logger.info("chave pix registrada com sucesso")

        return chavePix
    }
}