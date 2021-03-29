package br.com.zup

import br.com.zup.handle.ErrorHandler
import br.com.zup.model.ChavePix
import br.com.zup.ChavePix as ChavePixProto
import br.com.zup.requests.NovaChaveRequest
import br.com.zup.requests.toData
import br.com.zup.services.ConsultaChavePixService
import br.com.zup.services.ListarChavePixService
import br.com.zup.services.NovaChaveService
import br.com.zup.services.RemoveChaveService
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class PixServer(
    @Inject private val novaChaveService: NovaChaveService,
    @Inject private val removeChaveService: RemoveChaveService,
    @Inject private val consultaChavePixService: ConsultaChavePixService,
    @Inject private val listarChavePixService: ListarChavePixService
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

    override fun consultaChavePix(request: ConsultaChavePixRequest, responseObserver: StreamObserver<ConsultaChavePixResponse>) {

        val chavePixInfo = this.consultaChavePixService.consulta(request)

        responseObserver.onNext(ConsultaChavePixResponse.newBuilder()
            .setClienteId(chavePixInfo.clienteId)
            .setPixId(chavePixInfo.pixId)
            .setTitular(chavePixInfo.titular)
            .setCpf(chavePixInfo.cpf)
            .setTipoChave(chavePixInfo.tipoChave)
            .setValorChave(chavePixInfo.valorChave)
            .setConta(Conta.newBuilder()
                .setTipoConta(chavePixInfo.conta?.tipoConta?.descricao())
                .setAgencia(chavePixInfo.conta?.agencia)
                .setInstituicao(chavePixInfo.conta?.instituicao)
                .setNumero(chavePixInfo.conta?.numero)
                .build())
            .setCriado(chavePixInfo.criado.let {
                val createdAt = it?.atZone(ZoneId.of("UTC"))?.toInstant()

                com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(createdAt?.epochSecond!!)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build())
        responseObserver.onCompleted()
    }

    override fun listarChavePix(request: ListarChavePixRequest, responseObserver: StreamObserver<ListarChavePixResponse>) {
        val lista = this.listarChavePixService.listarChavePix(request.clienteId)

        val listaChavePixProto = ListarChavePixResponse.newBuilder()

        for (chavePix in lista.iterator()) {
            listaChavePixProto.addChavesPix(ChavePixProto.newBuilder()
                .setPixId(chavePix.id.toString())
                .setClienteId(chavePix.clienteId)
                .setTipoChave(chavePix.tipoChave)
                .setValorChave(chavePix.valorChave)
                .setTipoConta(TipoConta.valueOf(chavePix.tipoConta!!))
                .setCriacaoChave(chavePix.createdAt.let {
                    val createdAt = it?.atZone(ZoneId.of("UTC"))?.toInstant()

                    com.google.protobuf.Timestamp.newBuilder()
                        .setSeconds(createdAt?.epochSecond!!)
                        .setNanos(createdAt.nano)
                        .build()
                }))
        }

        responseObserver.onNext(listaChavePixProto.build())

        logger.info("Lista de chave PIX efetuada com sucesso")

        responseObserver.onCompleted()
    }
}