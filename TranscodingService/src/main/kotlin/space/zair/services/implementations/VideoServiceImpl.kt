package space.zair.services.implementations

import jakarta.inject.Singleton
import space.zair.model.dto.exception.ResourceNotFoundException
import space.zair.model.dto.request.TranscodedVideoRequest
import space.zair.model.entity.TranscodedVideo
import space.zair.model.repository.TranscodedVideoRepo
import space.zair.services.VideoService



@Singleton
class VideoServiceImpl(
    private val videoRepo: TranscodedVideoRepo
): VideoService {
    override fun getVideos(): Iterable<TranscodedVideo> = videoRepo.findAll()

    override fun getVideo(id: Long): TranscodedVideo = videoRepo.findById(id).orElseThrow() { ResourceNotFoundException() }

    override fun saveVideo(request: TranscodedVideoRequest): Result<TranscodedVideo> =
        runCatching {
            videoRepo.save(TranscodedVideo(request.name.substringBeforeLast("."),
                "/home/zair/Рабочий стол/Projects/WorkingWithVideos/resources/${request.quality}/${request.name}"))
        }
}