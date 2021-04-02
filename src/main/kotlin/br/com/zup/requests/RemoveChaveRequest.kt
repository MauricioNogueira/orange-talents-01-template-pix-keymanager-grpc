package br.com.zup.requests

import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class RemoveChaveRequest(

    @field:NotBlank
    val pixId: String?,

    @field:NotBlank
    val clienteId: String?
) {

}