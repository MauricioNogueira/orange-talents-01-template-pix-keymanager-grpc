package br.com.zup

import br.com.zup.cadastro.CriaRequestNovaChavePix
import br.com.zup.model.ChavePix
import br.com.zup.requests.NovaChaveRequest
import br.com.zup.services.NovaChaveService
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

@MicronautTest(transactional = false, rollback = false)
class CadastroChavePixTest{

    @Test
    fun `Cadastro com sucesso de uma nova chave PIX`() {
        val request = CriaRequestNovaChavePix()
            .tipoChave(TipoChave.RANDOM)
            .clienteId("5260263c-a3c1-4727-ae32-3bdb2538841b")
            .valorChave("")
            .tipoConta(TipoConta.CORRENTE)

        val chavePix = this.createModelChavePix(request.toRequest())

        `when`(novaChaveService().registrar(request.toRequest())).thenReturn(chavePix)

        chavePix.apply {
            id = null
        }

        Assertions.assertEquals("Yuri Matheus", chavePix.titular)
    }

    private fun createModelChavePix(request: NovaChaveRequest): ChavePix {
        return ChavePix().apply {
            id = UUID.randomUUID()
            tipoChave = request.tipoChave.name
            valorChave = request.valor
            tipoConta = request.tipoConta.name
            clienteId = request.idenficadorCliente
            participant = "60701190"
            branch = "0001"
            accountNumber = "291900"
            titular = "Yuri Matheus"
            cpf = "86135457004"
            createdAt = LocalDateTime.now()
        }
    }

    @MockBean(NovaChaveService::class)
    fun novaChaveService(): NovaChaveService {
        return mock(NovaChaveService::class.java)
    }
}