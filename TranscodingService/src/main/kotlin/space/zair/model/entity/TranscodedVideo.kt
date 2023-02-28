package space.zair.model.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class TranscodedVideo(
    @Column(nullable = false, length = 256)
    var name: String,

    @Column(nullable = false, length = 256)
    var quality: String
) : AbstractEntity() {
    constructor() : this("", "") {}
}