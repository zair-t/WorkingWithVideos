package space.zair.jobs

import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import space.zair.services.TranscodingService
import java.util.Collections

@Singleton
class OutBoxJob(
    private val transcodingService: TranscodingService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = "60s")
    fun executeFifteenMinutes() {
        logger.info("executing... set size: ${filenames.size}")

        if (filenames.isNotEmpty()) {
            filenames.forEach {
                transcodingService.send(it)
            }
        }
    }

    companion object {
        var filenames = Collections.synchronizedSet(HashSet<String>())
    }
}