package space.zair.model.dto.exception

import io.micronaut.http.HttpStatus

class ResourceNotFoundException : AbstractApiException(
    status = HttpStatus.NOT_FOUND,
    message = "Resource not found"
)