package com.gtomato.projects.backend.model.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment {

    @Id
    lateinit var id: UUID

    @Column(name = "content")
    var content: String? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.DETACH, CascadeType.REMOVE])
    @JoinColumn(name = "author_id")
    var author: User? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.DETACH, CascadeType.REMOVE])
    @JoinColumn(name = "book_id")
    var book: Book? = null

}