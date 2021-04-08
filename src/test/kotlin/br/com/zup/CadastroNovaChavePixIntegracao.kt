package br.com.zup

import br.com.zup.cadastro.CriaRequestNovaChavePix
import br.com.zup.repository.ChavePixRepository
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
class CadastroNovaChavePixIntegracao(
    @Inject val grpcClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub,
    @Inject val chavePixRepository: ChavePixRepository
) {

    @BeforeEach
    fun init() {
        this.chavePixRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar uma nova chave pix`() {
        val request = CriaRequestNovaChavePix()
            .tipoChave(TipoChave.RANDOM)
            .clienteId("5260263c-a3c1-4727-ae32-3bdb2538841b")
            .valorChave("")
            .tipoConta(TipoConta.CORRENTE)

        val response = this.grpcClient.novaChavePix(request.build())

        val optional = this.chavePixRepository.findById(UUID.fromString(response.chavePix))

        Assertions.assertTrue(optional.isPresent)
        Assertions.assertEquals(request.clienteId, optional.get().clienteId)
    }

    @Test
    fun `nao deve cadastrar chave pix igual`() {
        val request = CriaRequestNovaChavePix()
            .tipoChave(TipoChave.RANDOM)
            .clienteId("5260263c-a3c1-4727-ae32-3bdb2538841b")
            .valorChave("")
            .tipoConta(TipoConta.CORRENTE)

        this.grpcClient.novaChavePix(request.build())

        val error = assertThrows<StatusRuntimeException> {
            this.grpcClient.novaChavePix(request.build())
        }

        Assertions.assertEquals(Status.ALREADY_EXISTS.code, error.status.code)
    }

    @Test
    fun `nao deve cadastrar uma chave caso nao encontre o cliente`() {
        val request = CriaRequestNovaChavePix()
            .tipoChave(TipoChave.RANDOM)
            .clienteId(UUID.randomUUID().toString())
            .valorChave("")
            .tipoConta(TipoConta.CORRENTE)

        val error = assertThrows<StatusRuntimeException> {
            this.grpcClient.novaChavePix(request.build())
        }

        Assertions.assertEquals(Status.NOT_FOUND.code, error.status.code)
    }

    @Factory
    class grpcClientTest {

        @Singleton
        fun grpcStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub {
            return KeyManagerGrpcServiceGrpc.newBlockingStub(channel)
        }
    }
}
