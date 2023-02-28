package space.zair

import io.micronaut.runtime.Micronaut.run
import java.io.File


fun main(args: Array<String>) {
//	runCatching {
//		Files.createDirectories(Paths.get("resources/uploads"))
//	}
//		.onFailure { log.error ("Error creating uploads folder: ${it.message}") }
////		.onSuccess { log.info("Created folder uploads successfully: ${it.fileName}") }
//	val file: File = File("resources/uploads/Raindrops_VidevoMP4.mp4")
//	file.createNewFile()
//	val succeeded: Boolean
//	try {
//		val source = File("resources/uploads/Raindrops_Videvo.mp4")
//		val target = File("resources/uploads/Raindrops_VidevoMP4.mp4")
//
//		//Audio Attributes
//		val video = VideoAttributes()
//		//video.setQuality()
//		video.setCodec("h264")
////		video.setBitRate(128000)
//		//video.setQuality(480)
//		val sz: VideoSize  = VideoSize(1920, 1080)
//		video.setSize(sz)
//
//		//Encoding attributes
//		val attrs = EncodingAttributes()
//		attrs.setOutputFormat("mp4")
//		attrs.setVideoAttributes(video)
//
//		//Encode
//		val encoder = Encoder()
//		encoder.encode(MultimediaObject(source), target, attrs)
//	} catch (ex: Exception) {
//		ex.printStackTrace()
//		succeeded = false
//	}

	run(*args)
}

