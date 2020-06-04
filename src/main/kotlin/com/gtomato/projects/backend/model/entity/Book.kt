package com.gtomato.projects.backend.model.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
class Book {

    @Id
    lateinit var id: UUID

    @Column(name = "name")
    var name: String? = null

    @Column(name = "date")
    var publishDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.REMOVE])
    @JoinColumn(name = "author_id")
    var author: User? = null
}