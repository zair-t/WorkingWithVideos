package space.zair.services

import space.zair.model.dto.request.VideoRequest
import space.zair.model.entity.Video

interface VideoService {
    fun getVideos(): Iterable<Video>
    fun getVideo(id: Long): Video
    fun saveVideo(request: VideoRequest): Result<Video>
}