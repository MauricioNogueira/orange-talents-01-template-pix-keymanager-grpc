package br.com.zup.handle

import br.com.zup.PixServer
import br.com.zup.exceptions.DataRegisterException
import br.com.zup.exceptions.NotFoundException
import com.google.rpc.BadRequest
import io.grpc.Status
import com.google.rpc.Status as StatusGoogleRPC
import com.google.rpc.Code
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorHandler::class)
class ExceptionHandleInterceptor: MethodInterceptor<PixServer, Any?> {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun intercept(context: MethodInvocationContext<PixServer, Any?>): Any? {
        try {
            return context.proceed()
        } catch (e: Exception) {
            val error = when(e) {
                is IllegalArgumentException -> Status.INVALID_ARGUMENT.withDescription(e.message).asRuntimeException()
                is ConstraintViolationException -> constraintViolationExceptionHandle(e)
                is DataRegisterException -> Status.ALREADY_EXISTS.withDescription(e.message).asRuntimeException()
                is NotFoundException -> Status.NOT_FOUND.withDescription(e.message).asRuntimeException()
                else -> {
                    e.printStackTrace()
                    Status.UNKNOWN.withDescription("houve um erro interno no sistema").asRuntimeException()
                }
            }

            val responseObserver = context.parameterValues[1] as StreamObserver<*>
            responseObserver.onError(error)

            return null
        }
    }

    fun constraintViolationExceptionHandle(e: ConstraintViolationException): StatusRuntimeException {
        val badRequest = BadRequest.newBuilder()
        .addAllFieldViolations(e.constraintViolations.map {
            BadRequest.FieldViolation.newBuilder()
                .setField(it.propertyPath.last().name)
                .setDescription(it.message)
                .build()
        })
        .build()

        val statusProto = StatusGoogleRPC.newBuilder()
            .setCode(Code.INVALID_ARGUMENT_VALUE)
            .setMessage(e.message)
            .addDetails(com.google.protobuf.Any.pack(badRequest))
            .build()

        return StatusProto.toStatusRuntimeException(statusProto)
    }
}