package br.com.zup.requests

import br.com.zup.ConsultaChavePixRequest
import javax.validation.ConstraintViolationException
import javax.validation.Validator

fun ConsultaChavePixRequest.toModel(validator: Validator): Filtro {
    val filtro = when(filterCase) {
        ConsultaChavePixRequest.FilterCase.DADOSPIX -> dadosPix.let {
            Filtro.DadosPix(clienteId = this.dadosPix.clienteId, pixId = this.dadosPix.pixId)
        }

        ConsultaChavePixRequest.FilterCase.CHAVEPIX -> {
            Filtro.Chave(chavePix = this.chavePix)
        }

        ConsultaChavePixRequest.FilterCase.FILTER_NOT_SET -> {
            Filtro.Invalido()
        }
    }

    val violations = validator.validate(filtro)

    if (!violations.isEmpty()) {
        throw ConstraintViolationException(violations)
    }

    return filtro
}