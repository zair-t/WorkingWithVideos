package space.zair.services.jobs

import com.xuggle.xuggler.IContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import space.zair.model.entity.TranscodedVideo
import space.zair.model.repository.TranscodedVideoRepo
import ws.schild.jave.Encoder
import ws.schild.jave.MultimediaObject
import ws.schild.jave.encode.EncodingAttributes
import ws.schild.jave.encode.VideoAttributes
import ws.schild.jave.info.VideoSize
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

class Worker(private val filename: String, private val transcodedVideoRepo: TranscodedVideoRepo) : Runnable {
    override fun run() {
        //val container = IContainer.make()
        /* val container = IContainer.make()
         container.open("/app/uploads/$filename", IContainer.Type.READ, null)
         val width = container.getStream(0).streamCoder.width
         val height = container.getStream(0).streamCoder.height
         if (width >= 720 && height >= 480) {
             if (width < 1280) {
                 runBlocking {
                     launch {
                         createNewVideo("480p", filename, container)
                     }
                 }
             } else if (width < 1920) {
                 runBlocking {
                     launch {
                         createNewVideo("480p", filename, container)
                         createNewVideo("720p", filename, container)
                     }
                 }
             } else {*/
        runBlocking {
            launch {
                if (!transcodedVideoRepo.existsByNameAndQuality(filename.substringBeforeLast("."), "480p")) {
                    createNewVideo("480p", filename)
                    withContext(Dispatchers.IO) {
                        transcodedVideoRepo.save(TranscodedVideo(filename.substringBeforeLast("."), "480p"))
                    }
                }
                if (!transcodedVideoRepo.existsByNameAndQuality(filename.substringBeforeLast("."), "720p")) {
                    createNewVideo("720p", filename)
                    withContext(Dispatchers.IO) {
                        transcodedVideoRepo.save(TranscodedVideo(filename.substringBeforeLast("."), "720p"))
                    }
                }
                if (!transcodedVideoRepo.existsByNameAndQuality(filename.substringBeforeLast("."), "1080p")) {
                    createNewVideo("1080p", filename)
                    withContext(Dispatchers.IO) {
                        transcodedVideoRepo.save(TranscodedVideo(filename.substringBeforeLast("."), "1080p"))
                    }
                }
                executePost("http://outboxservice:8082/stop", "\"$filename\"")
            }
        }
        // }

    }
}

fun createNewVideo(quality: String, filename: String) {
    if (!Path("/app/$quality").exists())
        Path("/app/$quality").createDirectory()

    val target = File("/app/$quality/${filename.substringBeforeLast(".")}.mp4")
    target.createNewFile()

    //Properties of new video
    //val frameRate = container.getStream(0).streamCoder.frameRate.double.toInt()
    val bitRate: Int = when (quality) {
        "480p" -> 20971520 // 2.5 MB
        "720p" -> 41943040 // 5 MB
        else -> 67108864   // 8 MB
    }
    val videoSize: VideoSize = when (quality) {
        "480p" -> VideoSize(720, 480)
        "720p" -> VideoSize(1080, 720)
        else -> VideoSize(1920, 1080)
    }

    val targetVideoAttributes = VideoAttributes()
    targetVideoAttributes.setCodec("h264")
    targetVideoAttributes.setSize(videoSize)
    targetVideoAttributes.setFrameRate(24)
    targetVideoAttributes.setBitRate(bitRate)

    val attrs = EncodingAttributes()
    attrs.setOutputFormat("mp4")
    attrs.setVideoAttributes(targetVideoAttributes)

    val encoder = Encoder()
    encoder.encode(MultimediaObject(File("/app/uploads/$filename")), target, attrs)
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
