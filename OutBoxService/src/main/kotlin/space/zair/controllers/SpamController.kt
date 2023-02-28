package space.zair.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory
import space.zair.jobs.OutBoxJob

@Controller
class SpamController {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post("/start")
    fun startSpam(filename: String) {
        logger.info("Starting to spam for new file: $filename")
        OutBoxJob.filenames.add(filename)
    }

    @Post("/stop")
    fun stopSpam(filename: String) {
        logger.info("Stopping to spam for file: $filename")
        OutBoxJob.filenames.remove(filename)
    }
}