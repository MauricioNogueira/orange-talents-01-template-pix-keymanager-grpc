package br.com.zup.validations

import java.util.*

enum class TipoChave: TipoChaveValidator {
    CPF {
        override fun validate(value: String): Boolean {
            val regex: Regex = Regex(pattern = "^[0-9]{11}\$")

            if (regex.matches(value)) {
                return true
            }

            throw IllegalArgumentException("não está no formato de CPF")
        }
    },

    TELEFONE {
        override fun validate(value: String): Boolean {
            val regex: Regex = Regex(pattern = "^\\+[1-9][0-9]\\d{1,14}\$")

            if (regex.matches(value)) {
                return true
            }

            throw IllegalArgumentException("não está no formato de telefone")
        }
    },

    EMAIL {
        override fun validate(value: String): Boolean {
            val regex: Regex = Regex(pattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})\$")

            if (regex.matches(value)) {
                return true
            }

            throw IllegalArgumentException("não está no formato de email")
        }
    },

    CHAVE_ALEATORIA {
        override fun validate(value: String): Boolean {
            val regex: Regex = Regex(pattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}\$")

            println(value)
            println("Valor Regex: " + regex.matches(value))

            if (regex.matches(value)) {
                return true
            }

            throw IllegalArgumentException("não está no formato de uuid")
        }
    }
}