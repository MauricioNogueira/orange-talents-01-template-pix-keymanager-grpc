package br.com.zup.enuns

enum class Conta: ContaInterface {
    CORRENTE {
        override fun sigla(): String {
            return "CACC"
        }

        override fun descricao(): String {
            return "CONTA_CORRENTE"
        }
    },

    POUPANCA {
        override fun sigla(): String {
            return "CACC"
        }

        override fun descricao(): String {
            return "CONTA_POUPANCA"
        }
    }
}