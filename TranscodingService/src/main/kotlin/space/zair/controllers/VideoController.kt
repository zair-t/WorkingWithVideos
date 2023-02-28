package space.zair.controllers

import io.micronaut.http.annotation.*
import org.slf4j.LoggerFactory
import space.zair.model.dto.request.TranscodedVideoRequest
import space.zair.model.entity.TranscodedVideo
import space.zair.services.VideoService
import java.io.File

@Controller("/api")
class VideoController(
    private val videoService: VideoService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/videos")
    fun getVideos(): Iterable<TranscodedVideo> = videoService.getVideos()

    @Post("/videos")
    fun saveVideo(@Body transcodedVideoRequest: TranscodedVideoRequest) {
        videoService.saveVideo(transcodedVideoRequest)
        logger.info("requsting...")
    }

    @Get("/videos/{quality}/{name}")
    fun getVideo(@Body quality: String, @Body name: String): File = File("/app/$quality/$name.mp4")
}
