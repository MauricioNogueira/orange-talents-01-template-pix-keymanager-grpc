package br.com.zup.requests

import br.com.zup.dto.ChavePixInfo
import br.com.zup.dto.Conta
import br.com.zup.exceptions.NotFoundException
import br.com.zup.enuns.Conta as ContaEnum
import br.com.zup.repository.ChavePixRepository
import br.com.zup.response.ConsultaChavePixResponse
import br.com.zup.response.CreatePixKeyResponse
import br.com.zup.services.BCBService
import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

sealed class Filtro {

    val logger = LoggerFactory.getLogger(this.javaClass)

    abstract fun consultar(bcbServiceImpl: BCBService, chavePixRepository: ChavePixRepository): ChavePixInfo;

    @Introspected
    data class DadosPix(
        @field:NotBlank
        @field:ValidUUID
        val clienteId: String,

        @field:NotBlank
        @field:ValidUUID
        val pixId: String
        ) : Filtro() {

        override fun consultar(bcbServiceImpl: BCBService, chavePixRepository: ChavePixRepository): ChavePixInfo {
            val uuid = UUID.fromString(this.pixId)

            val optional = chavePixRepository.findByIdAndClienteId(uuid, this.clienteId)

            if (optional.isPresent) {
                val pix = optional.get()

                val result = bcbServiceImpl.consultar(pix.valorChave!!)

                val dadosConta = Conta(instituicao = "ITAÚ UNIBANCO S.A", agencia = pix.branch!!, numero = pix.accountNumber!!, tipoConta = ContaEnum.valueOf(pix.tipoConta!!))
                val chavePixInfo = ChavePixInfo().apply {
                    pixId = pix.id.toString()
                    clienteId = pix.clienteId!!
                    tipoChave = pix.tipoChave!!
                    valorChave = pix.valorChave!!
                    titular = result!!.owner?.name
                    cpf = result.owner?.taxIdNumber
                    conta = dadosConta
                    criado = LocalDateTime.now()
                }

                return chavePixInfo
            }

            logger.error("dados da da chave pix não foi encontrado, PIX ID: $pixId, Cliente ID: $clienteId")
            throw NotFoundException("cliente não foi encontrado")
        }
    }

    @Introspected
    data class Chave(
        @field:Size(max = 77)
        val chavePix: String
        ): Filtro() {

        override fun consultar(bcbServiceImpl: BCBService, chavePixRepository: ChavePixRepository): ChavePixInfo {
            val chavePixEntity = chavePixRepository.findByValorChave(chavePix).orElse(null)
            var result: ConsultaChavePixResponse? = null

            if (chavePixEntity == null) {
                logger.error("chave pix não foi encontrada no banco local")

                result = bcbServiceImpl.consultar(chavePix) ?: throw NotFoundException("não foi possível encontrar a chave pix no BCB")
            }

            val dadosConta = Conta(
                instituicao = "ITAÚ UNIBANCO S.A",
                agencia = chavePixEntity?.branch ?: result?.bankAccount?.branch!!,
                numero = chavePixEntity?.accountNumber ?: result?.bankAccount?.accountNumber!!,
                tipoConta = ContaEnum.valueOf(value = chavePixEntity?.tipoConta ?: if (result?.bankAccount?.accountType!! == "CACC") "CORRENTE" else "POUPANCA")
            )

            val chavePixInfo = ChavePixInfo().apply {
                tipoChave = chavePixEntity?.tipoChave ?: result?.keyType
                valorChave = chavePixEntity?.valorChave ?: result?.key
                titular = chavePixEntity?.titular ?: result?.owner?.name
                cpf = chavePixEntity?.cpf ?: result?.owner?.taxIdNumber
                conta = dadosConta
                criado = LocalDateTime.now() ?: result?.createdAt
            }

            return chavePixInfo
        }
    }

    @Introspected
    class Invalido() : Filtro() {
        override fun consultar(bcbServiceImpl: BCBService, chavePixRepository: ChavePixRepository): ChavePixInfo {
            throw IllegalArgumentException("você não está enviando os dados do pix ou chave pix")
        }
    }
}