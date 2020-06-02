package com.gtomato.projects.backend.model

import org.springframework.data.annotation.Id
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "widget")
open class Widget: Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "value", nullable = false)
    public var value: String? = null


}