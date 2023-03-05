package space.zair.model.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import space.zair.model.entity.Video
import java.awt.print.Pageable

@Repository
interface VideoRepo: CrudRepository<Video, Long> {
//    fun findAllByOrderByIdAsc(): Iterable<Video>
    fun findOrderByIdAsc(): Iterable<Video>
}