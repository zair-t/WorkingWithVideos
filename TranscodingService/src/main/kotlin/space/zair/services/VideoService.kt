package space.zair.services

import space.zair.model.dto.request.TranscodedVideoRequest
import space.zair.model.entity.TranscodedVideo

interface VideoService {
    fun getVideos(): Iterable<TranscodedVideo>
    fun getVideo(id: Long): TranscodedVideo
    fun saveVideo(request: TranscodedVideoRequest): Result<TranscodedVideo>
}