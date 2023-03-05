package space.zair.model.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.PageableRepository
import space.zair.model.entity.Video
import java.awt.print.Pageable

@Repository
interface VideoRepo: PageableRepository<Video, Long>