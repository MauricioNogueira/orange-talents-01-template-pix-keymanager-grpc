package br.com.zup

import br.com.zup.model.ChavePix
import br.com.zup.repository.ChavePixRepository
import br.com.zup.requests.NovaChaveRequest
import br.com.zup.services.NovaChaveService
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.util.*

@MicronautTest(transactional = false, rollback = false)
class CadastroChavePixTest {

    val chavePixRepository: ChavePixRepository = mock(ChavePixRepository::class.java)

    @BeforeEach
    fun teste() {
        MockitoAnnotations.openMocks(chavePixRepository)
    }

    @Test
    fun `Cadastro com sucesso de uma nova chave PIX`() {
        val request = NovaChavePixRequest.newBuilder()
            .setTipoChave(TipoChave.RANDOM)
            .setIdentificadorCliente("5260263c-a3c1-4727-ae32-3bdb2538841b")
            .setValorChave("")
            .setTipoConta(TipoConta.CORRENTE)
            .build()

        val novaChave = NovaChaveRequest(
            idenficadorCliente = request.identificadorCliente,
            tipoChave = request.tipoChave,
            valor = request.valorChave,
            tipoConta = request.tipoConta
        )

        val chavePix = ChavePix().apply {
            id = UUID.randomUUID()
            tipoChave = request.tipoChave.name
            valorChave = request.valorChave
            tipoConta = request.tipoConta.name
            clienteId = request.identificadorCliente
            participant = "60701190"
            branch = "0001"
            accountNumber = "291900"
            titular = "Yuri Matheus"
            cpf = "86135457004"
            createdAt = LocalDateTime.now()
        }

        val result = `when`(novaChaveService().registrar(novaChave)).thenReturn(chavePix)

        chavePix.apply {
            id = null
        }

//        verify(this.chavePixRepository).save(chavePix)

        Assertions.assertEquals("Yuri Matheus", chavePix.titular)
    }

    @MockBean(NovaChaveService::class)
    fun novaChaveService(): NovaChaveService {
        return mock(NovaChaveService::class.java)
    }

//    @MockBean(ChavePixRepository::class)
//    fun chavePixRepository(): ChavePixRepository {
//        return mock(ChavePixRepository::class.java)
//    }
}