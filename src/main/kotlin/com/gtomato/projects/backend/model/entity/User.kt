package com.gtomato.projects.backend.model.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "_user")
class User: Serializable {
    @Id
    lateinit var id: UUID

    @Column(name = "name")
    var name: String? = null

    @Column(name = "avatar")
    var avatar: String? = null
}