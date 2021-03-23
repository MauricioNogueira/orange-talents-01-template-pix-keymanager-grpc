package br.com.zup.requests

import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class RemoveChaveRequest(

    @field:NotBlank
    val key: String?,

    @field:NotBlank
    val participant: String?
) {

}