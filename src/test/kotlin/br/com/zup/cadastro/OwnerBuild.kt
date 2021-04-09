package br.com.zup.cadastro

import br.com.zup.dto.Owner

class OwnerBuild {
    var type: String? = ""
    var name: String? = ""
    var taxIdNumber: String? = ""

    fun setType(type: String): OwnerBuild {
        this.type = type
        return this
    }

    fun setName(name: String): OwnerBuild {
        this.name = name
        return this
    }

    fun setTaxIdNumber(taxIdNumber: String): OwnerBuild {
        this.taxIdNumber = taxIdNumber
        return this
    }

    fun build(): Owner {
        return Owner(type = type!!, name = name!!, taxIdNumber = taxIdNumber!!)
    }
}