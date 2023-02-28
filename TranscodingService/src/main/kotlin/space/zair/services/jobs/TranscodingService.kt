package space.zair.services.jobs

import com.xuggle.xuggler.IContainer
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import space.zair.model.repository.TranscodedVideoRepo
import java.util.concurrent.Executors

@RabbitListener
class TranscodingService(
    private val transcodedVideoRepo: TranscodedVideoRepo
) {
    private val executor = Executors.newCachedThreadPool()

    @Queue("transcodingVideoQueue")
    fun receive(filename: String) {
        val worker = Worker(filename, transcodedVideoRepo)
        executor.execute(worker)
    }

}