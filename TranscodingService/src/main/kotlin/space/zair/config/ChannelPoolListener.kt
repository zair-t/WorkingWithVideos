package space.zair.config

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton
import java.io.IOException

@Singleton
class ChannelPoolListener : ChannelInitializer() {
    @Throws(IOException::class)
    override fun initialize(channel: Channel, name: String) {
        channel.queueDeclare("transcodingVideoQueue", true, false, false, null)
    }
}