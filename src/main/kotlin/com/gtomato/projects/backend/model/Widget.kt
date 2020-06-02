package com.gtomato.projects.backend.model

import com.expediagroup.graphql.spring.operations.Query
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "widget")
class Widget: Serializable, Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private var id: Int? = null

    @Column(name = "value", nullable = false)
    var value: String? = null
}