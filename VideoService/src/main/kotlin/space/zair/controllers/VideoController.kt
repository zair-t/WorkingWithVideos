package space.zair.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import org.slf4j.LoggerFactory
import space.zair.model.dto.request.VideoRequest
import space.zair.model.entity.Video
import space.zair.services.VideoService
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Controller("/api")
class VideoController(
    private val videoService: VideoService,
   // private val transcodingService: TranscodingService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/videos")
    fun getVideos(): Iterable<Video> = videoService.getVideos()

    // does not work
    @Get("/videos/page/{num}")
    fun getPage(@PathVariable num: Int, @QueryValue numOnPage: Int)
        = videoService.getPageWithVideos(numOnPage, num)

    // I thought CompletedFileUpload will write data to memory, not to disk.
    // As I remember I even got outOfMemoryException while using this...
    // But it seems to work here
    @Post("/videos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    fun saveVideo(@Body file: CompletedFileUpload) {
        // As I understood OutBox pattern, saving video to database and
        // adding message to OutBox should be in one transaction.
        // File name should be uniq (for example id.ext)
        videoService.saveVideo(VideoRequest(file.filename, file))
        logger.info("requsting...")
        executePost("http://outboxservice:8082/start", "\""+file.filename+"\"")
        // transcodingService.send(file.filename)
    }

    @Get("/videos/{id}")
    fun getVideo(@Body id: Long): Video = videoService.getVideo(id)
}

fun executePost(targetURL: String?, urlParameters: String): String? {
    var connection: HttpURLConnection? = null
    return try {
        val url = URL(targetURL)
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty(
            "Content-Type",
            "application/json"
        )
        connection.setRequestProperty(
            "Content-Length",
            urlParameters.toByteArray().size.toString()
        )
        connection.setRequestProperty("Content-Language", "en-US")
        connection.useCaches = false
        connection.doOutput = true

        //Send request
        val wr = DataOutputStream(
            connection.outputStream
        )
        wr.writeBytes(urlParameters)
        wr.close()

        //Get Response
        val `is` = connection.inputStream
        val rd = BufferedReader(InputStreamReader(`is`))
        val response = StringBuilder() // or StringBuffer if Java version 5+
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            response.append(line)
            response.append('\r')
        }
        rd.close()
        response.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        connection?.disconnect()
    }
}