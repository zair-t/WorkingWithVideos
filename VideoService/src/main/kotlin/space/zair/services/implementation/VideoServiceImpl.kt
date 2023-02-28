package space.zair.services.implementation

import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import space.zair.model.dto.exception.ResourceNotFoundException
import space.zair.model.dto.request.VideoRequest
import space.zair.model.entity.Video
import space.zair.model.repository.VideoRepo
import space.zair.services.VideoService
import java.nio.file.Files
import java.nio.file.Paths
import org.slf4j.Logger

@Singleton
class VideoServiceImpl(
    private val videoRepo: VideoRepo
): VideoService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun getVideos(): Iterable<Video> = videoRepo.findAll()

    override fun getVideo(id: Long): Video = videoRepo.findById(id).orElseThrow() { ResourceNotFoundException() }

    override fun saveVideo(request: VideoRequest): Result<Video> =
        runCatching {
            Files.copy(request.file.inputStream, Paths.get("/app").resolve(request.file.filename))
            videoRepo.save(Video(request.name.substringBeforeLast("."),
                "/home/zair/Рабочий стол/Projects/WorkingWithVideos/resources/uploads/" + request.file.filename))
        }.onFailure {
            log.warn("Error uploading file ${request.file.filename} reason: ${it.javaClass}")
        }
}