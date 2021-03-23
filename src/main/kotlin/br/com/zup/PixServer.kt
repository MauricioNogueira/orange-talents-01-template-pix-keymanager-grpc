package br.com.zup

import br.com.zup.handle.ErrorHandler
import br.com.zup.model.ChavePix
import br.com.zup.requests.NovaChaveRequest
import br.com.zup.requests.toData
import br.com.zup.services.NovaChaveService
import br.com.zup.services.RemoveChaveService
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
@ErrorHandler
class PixServer(
    @Inject private val novaChaveService: NovaChaveService,
    @Inject private val removeChaveService: RemoveChaveService
    ) : KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceImplBase() {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun novaChavePix(request: NovaChavePixRequest, responseObserver: StreamObserver<NovaChavePixResponse>) {

        val novaChaveRequest = NovaChaveRequest(request.identificadorCliente, request.tipoChave, request.valorChave, request.tipoConta)

        val chavePix: ChavePix = this.novaChaveService.registrar(novaChaveRequest)

        val response = NovaChavePixResponse.newBuilder()
            .setChavePix(chavePix.id.toString())
            .setClienteId(chavePix.clienteId)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun removeChavePix(
        request: RemoveChavePixRequest,
        responseObserver: StreamObserver<RemoveChavePixResponse>
    ) {

        val req = request.toData()

        this.removeChaveService.remove(req)

        val response = RemoveChavePixResponse.newBuilder()
            .setMensagem("chave pix removida com sucesso")
            .build()


        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}