package br.com.zup.services

import br.com.zup.enuns.Conta
import br.com.zup.model.ChavePix
import br.com.zup.requests.BankAccount
import br.com.zup.requests.CreatePixKeyRequest
import br.com.zup.requests.Owner
import br.com.zup.response.ContaClienteResponse
import br.com.zup.validations.TipoChave
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import br.com.zup.requests.DeletePixKeyRequest

@Singleton
class BCBService(@Inject private val bcbClient: BCBClient) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    fun cadastrarChavePix(contaClienteResponse: ContaClienteResponse, chavePix: ChavePix) {

        val request = CreatePixKeyRequest(
            keyType = TipoChave.valueOf(chavePix.tipoChave!!).name,
            key = chavePix.valorChave!!,
            bankAccount = BankAccount(
                participant = contaClienteResponse.instituicao.ispb,
                branch = contaClienteResponse.agencia,
                accountNumber = contaClienteResponse.numero,
                accountType = Conta.valueOf(chavePix.tipoConta!!).sigla()
            ),
            owner = Owner(
                type = "NATURAL_PERSON",
                name = contaClienteResponse.titular.nome,
                taxIdNumber = contaClienteResponse.titular.cpf
            )
        )

        bcbClient.registarChavePix(request)

        logger.info("chave pix registrarda no BCB")
    }

    fun removeChavePix(chavePix: ChavePix) {
        val request = DeletePixKeyRequest(
            key = chavePix.valorChave!!,
            participant = chavePix.participant!!
        )

        bcbClient.removeChavePix(request, chavePix.valorChave!!)

        logger.info("chave pix foi removida do BCB")
    }
}