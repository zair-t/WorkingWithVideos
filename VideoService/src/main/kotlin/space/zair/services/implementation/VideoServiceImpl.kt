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

    override fun getPageWithVideos(numOnPage: Int, page: Int): List<Video> {
        val list: MutableList<Video> = mutableListOf()
        videoRepo.findOrderByIdAsc().forEach{list += it}
        return if (list.size >= page * numOnPage) {
            list.slice((page - 1) * numOnPage .. page * numOnPage)
        } else if ( (page - 1) * numOnPage <= list.size && list.size <= page * numOnPage) {
            list.slice((page - 1) * numOnPage .. list.size)
        } else {
            emptyList()
        }
//        return list.slice((page - 1) * numOnPage .. page * numOnPage)
    }

    override fun getVideo(id: Long): Video = videoRepo.findById(id).orElseThrow() { ResourceNotFoundException() }

    override fun saveVideo(request: VideoRequest): Result<Video> =
        runCatching {
            Files.copy(request.file.inputStream, Paths.get("/app").resolve(request.file.filename))
            videoRepo.save(Video(request.name.substringBeforeLast("."),
                // Path from root with russian letters. And it seems not to be used anywhere,
                // because in docker this path is /app/
                "/home/zair/Рабочий стол/Projects/WorkingWithVideos/resources/uploads/" + request.file.filename))
        }.onFailure {
            log.warn("Error uploading file ${request.file.filename} reason: ${it.javaClass}")
        }
}