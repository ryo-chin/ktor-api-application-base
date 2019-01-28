package com.example.model

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val age = integer("age")
    val dateUpdated = long("dateUpdated")
}


data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val dateUpdated: Long
)


data class NewUser(
    val id: Int?,
    val name: String,
    val age: Int
)