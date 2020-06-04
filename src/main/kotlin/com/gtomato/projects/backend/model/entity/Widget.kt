package com.gtomato.projects.backend.model.entity


import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "widget")
class Widget: Serializable {
    @Id
    var id: Int? = null

    @Column(name= "value")
    var value: String? = null
}