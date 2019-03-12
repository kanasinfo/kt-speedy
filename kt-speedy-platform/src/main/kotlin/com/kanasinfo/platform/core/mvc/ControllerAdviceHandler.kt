package com.kanasinfo.platform.core.mvc

import com.kanasinfo.platform.core.bean.ErrorResponse
import com.kanasinfo.platform.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class ControllerAdviceHandler {
    val logger = LoggerFactory.getLogger(ControllerAdviceHandler::class.java)!!
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun requestHandlingNoHandlerFound(request: HttpServletRequest, response: HttpServletResponse, e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        return if (e is BusinessException) {
            response.setHeader("i-critical", "0")
            ResponseEntity(
                    ErrorResponse(message = e.message, code = e.code, msg = e.message),
                    e.httpCode ?: HttpStatus.FORBIDDEN
            )
        } else {
            response.setHeader("i-critical", "1")
            ResponseEntity(ErrorResponse(message = e.message, code = e.javaClass.name, msg = e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}