package br.com.zup.services

import br.com.zup.exceptions.DataRegisterException
import br.com.zup.requests.NovaChave
import br.com.zup.repository.ChavePixRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid
import br.com.zup.model.ChavePix
import br.com.zup.validations.TipoChave
import org.slf4j.Logger
import java.util.*

@Validated
@Singleton
class NovaChaveService(
    @Inject private val itauService: ItauService,
    @Inject private val chavePixRepository: ChavePixRepository
    ) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun registrar(@Valid novaChave: NovaChave): ChavePix {

        val contaCliente = this.itauService.buscaCliente(novaChave.idenficadorCliente, novaChave.tipoConta.name) ?: throw IllegalArgumentException("error")

        val optional: Optional<ChavePix> = this.chavePixRepository.findByClienteIdAndTipoConta(
            contaCliente.titular.id,
            novaChave.tipoConta.name
        )

        if (optional.isPresent) {
            logger.error("chave pix já foi cadastrada: ${optional.get()}")

            throw DataRegisterException("chave pix já foi cadastrada para esta conta")
        }

        var chavePix: ChavePix = novaChave.toModel()

        TipoChave.valueOf(novaChave.tipoChave.name).validate(chavePix.valorChave!!)

        chavePix = this.chavePixRepository.save(chavePix)

        logger.info("chave pix registrada com sucesso")

        return chavePix
    }
}