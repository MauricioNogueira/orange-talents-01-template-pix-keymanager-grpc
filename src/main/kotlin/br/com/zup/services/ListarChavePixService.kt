package br.com.zup.services

import br.com.zup.model.ChavePix
import br.com.zup.repository.ChavePixRepository
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListarChavePixService(
    @Inject val chavePixRepository: ChavePixRepository
) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    fun listarChavePix(clienteId: String): List<ChavePix> {
        val lista = this.chavePixRepository.findByClienteId(clienteId)

        return lista
    }
}