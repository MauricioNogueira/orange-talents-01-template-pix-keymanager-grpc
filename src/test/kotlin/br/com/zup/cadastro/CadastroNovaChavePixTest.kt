package br.com.zup.cadastro

import br.com.zup.*
import br.com.zup.enuns.Conta
import br.com.zup.model.ChavePix
import br.com.zup.repository.ChavePixRepository
import br.com.zup.requests.CreatePixKeyRequest
import br.com.zup.response.ContaClienteResponse
import br.com.zup.response.CreatePixKeyResponse
import br.com.zup.response.Instituicao
import br.com.zup.response.Titular
import br.com.zup.services.BCBClient
import br.com.zup.services.ItauClient
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
class CadastroNovaChavePixTest(
    @Inject val grpcClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub,
    @Inject val chavePixRepository: ChavePixRepository
) {

    @Inject
    lateinit var itauClient: ItauClient

    @Inject
    lateinit var bcbClient: BCBClient

    @BeforeEach
    fun init() {
        this.chavePixRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar uma nova chave pix`() {
        val request = this.criaRequestNovaChavePixBuild(
            tipoChave = TipoChave.EMAIL,
            clienteId = "5260263c-a3c1-4727-ae32-3bdb2538841b",
            valorChave = "mauricio.oliveira@zup.com.br",
            tipoConta = TipoConta.CORRENTE
        )

        val contaClienteResponse = this.respostaItauClient(
            agencia = "0001",
            numeroConta = "123455",
            nameTipoChave = request.tipoChave!!.name,
            titular = TitularBuild().id("5260263c-a3c1-4727-ae32-3bdb2538841b").nome("Yuri Matheus").cpf("86135457004").build(),
            instituicao = InstituicaoBuild().nome("ITAÚ UNIBANCO S.A.").ispb("60701190").build()
        )

        val chavePix = ChavePixBuild()
            .setTipoChave(contaClienteResponse.tipo)
            .setValorChave(request.valorChave!!)
            .setTipoConta(request.tipoConta!!.name)
            .setClienteId(contaClienteResponse.titular.id)
            .setParticipant(contaClienteResponse.instituicao.ispb)
            .setBranch(contaClienteResponse.agencia)
            .setAccountNumber(contaClienteResponse.numero)
            .setTitular(contaClienteResponse.titular.nome)
            .setCpf(contaClienteResponse.titular.cpf).build()

        Mockito.`when`(this.itauClient.buscaCliente(clienteId = "5260263c-a3c1-4727-ae32-3bdb2538841b", tipo = "CONTA_CORRENTE"))
            .thenReturn(contaClienteResponse)

        val requestBcbClient = this.createChavePixRequestBuild(chavePix)

        Mockito.`when`(this.bcbClient.registarChavePix(requestBcbClient))
            .thenReturn(this.createChavePixResponseBuild(chavePix))

        val response = this.grpcClient.novaChavePix(request.build())

        val chavePixBanco = this.chavePixRepository.findByClienteId(response.clienteId).get(0)

        Assertions.assertEquals(chavePix.clienteId, chavePixBanco.clienteId)
        Assertions.assertEquals("EMAIL", chavePixBanco.tipoChave)
        Assertions.assertEquals("CORRENTE", chavePixBanco.tipoConta)
    }

    @Factory
    class grpcClientTest {

        @Singleton
        fun grpcStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub {
            return KeyManagerGrpcServiceGrpc.newBlockingStub(channel)
        }
    }

    @MockBean(BCBClient::class)
    fun bcbClient(): BCBClient {
        return Mockito.mock(BCBClient::class.java)
    }

    @MockBean(ItauClient::class)
    fun itauClient(): ItauClient {
        return Mockito.mock(ItauClient::class.java)
    }

    fun createChavePixRequestBuild(chavePix: ChavePix): CreatePixKeyRequest {
        return CreateChavePixRequestBuild()
            .setKeyType(TipoChave.EMAIL.name)
            .setKey(chavePix.valorChave!!)
            .setBankAccount(
                BankAccountBuild()
                .setParticipant(chavePix.participant!!)
                .setAccountNumber(chavePix.accountNumber!!)
                .setBranch(chavePix.branch!!)
                .setAccountType(Conta.valueOf(chavePix.tipoConta!!).sigla())
                .build())
            .setOwner(
                OwnerBuild()
                .setName(chavePix.titular!!)
                .setType("NATURAL_PERSON")
                .setTaxIdNumber(chavePix.cpf!!)
                .build())
            .build()
    }

    fun createChavePixResponseBuild(chavePix: ChavePix): CreatePixKeyResponse {
        val result = CreateChavePixResponseBuild()
            .setKeyType(TipoChave.EMAIL.name)
            .setKey(chavePix.valorChave!!)
            .setBankAccount(
                BankAccountBuild()
                .setParticipant(chavePix.participant!!)
                .setAccountNumber(chavePix.accountNumber!!)
                .setBranch(chavePix.branch!!)
                .setAccountType(chavePix.tipoConta!!)
                .build())
            .setOwner(
                OwnerBuild()
                .setName(chavePix.titular!!)
                .setType("NATURAL_PERSON")
                .setTaxIdNumber(chavePix.cpf!!)
                .build())
            .build()

        result.createdAt = LocalDateTime.now()

        return result
    }

    fun criaRequestNovaChavePixBuild(tipoChave: TipoChave, clienteId: String, valorChave: String, tipoConta: TipoConta): CriaRequestNovaChavePixBuild {
        val request = CriaRequestNovaChavePixBuild()
            .tipoChave(tipoChave)
            .clienteId(clienteId)
            .valorChave(valorChave)
            .tipoConta(tipoConta)

        return request
    }

    fun respostaItauClient(agencia: String, numeroConta: String, nameTipoChave: String, titular: Titular, instituicao: Instituicao): ContaClienteResponse {
        return ContaBuild()
            .setAgencia(agencia)
            .setNumero(numeroConta)
            .setTipo(nameTipoChave)
            .setTitular(titular)
            .setInstituicao(instituicao)
            .build()
    }
}
