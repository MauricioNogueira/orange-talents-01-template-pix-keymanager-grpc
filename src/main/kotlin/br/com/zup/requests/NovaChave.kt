package br.com.zup.requests

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.model.ChavePix
import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
class NovaChave(

    @field:ValidUUID
    @field:NotBlank
    val idenficadorCliente: String,

    @field:NotNull
    val tipoChave: TipoChave,

    @field:Size(max = 77)
    val valor: String? = "",

    @field:NotNull
    val tipoConta: TipoConta
) {

    fun toModel(): ChavePix {
        val chave: String = this.tipoChave.name
        val conta: String = this.tipoConta.name

        return ChavePix().apply {
            tipoChave =  chave
            valorChave = if (valor!!.isEmpty() && chave.equals("CHAVE_ALEATORIA")) UUID.randomUUID().toString() else valor
            tipoConta = conta
            clienteId = idenficadorCliente
        }
    }

    override fun toString(): String {
        return "NovaChave(idenficadorCliente='$idenficadorCliente', tipoChave=${tipoChave.name}, valor='$valor', tipoConta=${tipoConta.name})"
    }
}