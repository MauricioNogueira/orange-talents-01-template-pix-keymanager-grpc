package br.com.zup.repository

import br.com.zup.TipoConta
import br.com.zup.model.ChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository: JpaRepository<ChavePix, UUID> {
    fun findByClienteIdAndTipoContaAndTipoChave(clienteId: String, tipoConta: String, tipoChave: String): Optional<ChavePix>
    fun findByValorChaveAndParticipant(key: String, pariticipant: String): Optional<ChavePix>
}