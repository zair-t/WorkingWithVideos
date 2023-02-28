package space.zair.model.dto.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.micronaut.http.HttpStatus
import space.zair.model.dto.ApiResponse
import java.time.LocalDateTime

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage")
abstract class AbstractApiException(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse, Exception() {
    override val timestamp: LocalDateTime = LocalDateTime.now()
}