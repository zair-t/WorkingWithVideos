package space.zair.model.dto.request

import io.micronaut.http.multipart.CompletedFileUpload

class VideoRequest(
    val name: String,
    val file: CompletedFileUpload
)