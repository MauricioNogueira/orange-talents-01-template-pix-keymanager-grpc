package br.com.zup.requests

import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class RemoveChaveRequest(

    @field:ValidUUID
    @field:NotBlank
    val pixId: String?,

    @field:ValidUUID
    @field:NotBlank
    val clienteId: String?
) {

}