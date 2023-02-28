package space.zair.model.dto

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import java.time.LocalDateTime

interface ApiResponse {
    val status: HttpStatus
    val message: String
    val timestamp: LocalDateTime

    fun asResponse(): MutableHttpResponse<ApiResponse> = HttpResponse.status<String>(status).body(this)
}