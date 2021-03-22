package br.com.zup.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
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

    override fun toString(): String {
        return "ChavePix [id=$id, tipoChave=$tipoChave, valorChave=$valorChave, tipoConta=$tipoConta, clienteId=$clienteId]"
    }
}