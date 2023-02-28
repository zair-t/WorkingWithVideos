package space.zair.model.dto.request

import io.micronaut.http.multipart.CompletedFileUpload

class TranscodedVideoRequest(
    val name: String,
    val quality: String
)