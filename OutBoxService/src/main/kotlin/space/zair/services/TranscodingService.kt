package space.zair.services

import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient
interface TranscodingService {
    @Binding("transcodingVideoQueue")
    fun send(filename: String)
}