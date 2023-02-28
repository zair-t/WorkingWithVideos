package space.zair.model.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Video(
    @Column(nullable = false, length = 256)
    var name: String,

    @Column(nullable = false, length = 256)
    var path: String
) : AbstractEntity() {
    constructor() : this("", "") {}
}