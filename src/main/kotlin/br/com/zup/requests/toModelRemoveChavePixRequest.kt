package br.com.zup.requests

import br.com.zup.RemoveChavePixRequest

fun RemoveChavePixRequest.toData(): RemoveChaveRequest {
    return RemoveChaveRequest(this.key, this.participant)
}