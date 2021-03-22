package br.com.zup

import br.com.zup.exceptions.DataRegisterException
import br.com.zup.handle.ErrorHandler
import br.com.zup.model.ChavePix
import br.com.zup.requests.NovaChave
import br.com.zup.services.NovaChaveService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException
import com.google.rpc.Status as StatusGoogle
import com.google.rpc.Code
import io.grpc.protobuf.StatusProto
import javax.transaction.Transactional

@Singleton
@ErrorHandler
class PixServer(@Inject private val novaChaveService: NovaChaveService) : KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceImplBase() {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun novaChavePix(request: NovaChavePixRequest, responseObserver: StreamObserver<NovaChavePixResponse>) {

        val novaChave = NovaChave(request.identificadorCliente, request.tipoChave, request.valorChave, request.tipoConta)

        val chavePix: ChavePix = this.novaChaveService.registrar(novaChave)

        val response = NovaChavePixResponse.newBuilder()
            .setChavePix(chavePix.id.toString())
            .setClienteId(chavePix.clienteId)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}