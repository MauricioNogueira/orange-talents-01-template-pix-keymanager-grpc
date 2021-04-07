package br.com.zup.model

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Introspected
@Table(name = "chaves_pix")
class ChavePix {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @field:NotBlank
    var tipoChave: String? = ""

    @field:Size(max = 77)
    @field:NotBlank
    var valorChave: String? = ""

    @field:NotBlank
    var tipoConta: String? = ""

    @field:NotBlank
    var clienteId: String? = ""

    @field:NotBlank
    var participant: String? = ""

    @field:NotBlank
    var branch: String? = ""

    @field:NotBlank
    var accountNumber: String? = ""

    @field:NotBlank
    var titular: String? = ""

    @field:NotBlank
    var cpf: String? = ""

    @field:NotNull
    var createdAt: LocalDateTime? = null

    override fun toString(): String {
        return "ChavePix(id=$id, tipoChave=$tipoChave, valorChave=$valorChave, tipoConta=$tipoConta, clienteId=$clienteId, participant=$participant, branch=$branch, accountNumber=$accountNumber, titular=$titular, cpf=$cpf, createdAt=$createdAt)"
    }
}