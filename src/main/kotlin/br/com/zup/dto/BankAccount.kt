package br.com.zup.dto

class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: String
) {
    override fun toString(): String {
        return "BankAccount(participant='$participant', branch='$branch', accountNumber='$accountNumber', accountType='$accountType')"
    }
}