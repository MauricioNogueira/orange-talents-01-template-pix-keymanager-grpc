package br.com.zup.services

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.DadosPix
import br.com.zup.dto.ChavePixInfo
import br.com.zup.repository.ChavePixRepository
import br.com.zup.requests.Filtro
import br.com.zup.requests.toModel
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Validator

@Singleton
class ConsultaChavePixService(
    @Inject private val bcbServiceImpl: BCBServiceImpl,
    @Inject private val chavePixRepository: ChavePixRepository,
    @Inject private val validator: Validator
) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    fun consulta(request: ConsultaChavePixRequest): ChavePixInfo {
        val filtro = request.toModel(validator)
        val chavePixInfo = filtro.consultar(bcbServiceImpl, chavePixRepository)

        return chavePixInfo
    }
}