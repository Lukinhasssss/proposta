package br.com.lukinhasssss.proposta.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ControllerExceptionHandler {

    private val logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<StandardErrorMessage> {
        val errorMessages = exception.bindingResult.fieldErrors.map { FieldError(it.field, it.defaultMessage.toString()) }.toSet()
        val errorResponse = StandardErrorMessage(status = HttpStatus.BAD_REQUEST.value(), path = request.servletPath, messages = errorMessages)

        logger.error("Error to make request on path ${request.servletPath}, errors: $errorResponse")
        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(value = [AlreadyExistsException::class])
    fun handleAlreadyExistsException(exception: AlreadyExistsException, request: HttpServletRequest): ResponseEntity<AlreadyExistsExceptionMessage> {
        val fieldError = FieldError(exception.field, exception.message)
        val errorResponse = AlreadyExistsExceptionMessage(status = HttpStatus.UNPROCESSABLE_ENTITY.value(), path = request.servletPath, message = fieldError)

        logger.error("Error to make request on route: ${request.servletPath}, error: $errorResponse")

        return ResponseEntity.unprocessableEntity().body(errorResponse)
    }

    @ExceptionHandler(value = [IntegrationErrorException::class])
    fun handleIntegrationErrorException(exception: IntegrationErrorException, request: HttpServletRequest): ResponseEntity<FieldError> {
        val errorResponse = FieldError(field = "", message = exception.message!!)

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse)
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(exception: NotFoundException, request: HttpServletRequest): ResponseEntity<FieldError> {
        val message = FieldError(field = "", message = exception.localizedMessage)

        logger.info("${exception.localizedMessage}, url: ${request.requestURL}")

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message)
    }

}