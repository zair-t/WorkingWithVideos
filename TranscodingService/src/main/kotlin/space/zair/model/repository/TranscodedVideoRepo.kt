package space.zair.model.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import space.zair.model.entity.TranscodedVideo

@Repository
interface TranscodedVideoRepo: CrudRepository<TranscodedVideo, Long> {
    fun existsByNameAndQuality(name: String, quality: String): Boolean
}