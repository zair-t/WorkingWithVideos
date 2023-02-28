package space.zair.model.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import space.zair.model.entity.Video

@Repository
interface VideoRepo: CrudRepository<Video, Long>