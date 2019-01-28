package com.example.service

import com.example.model.User
import com.example.model.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface UserService {
    fun fetchAll() : List<User>
    fun fetch(id: Int) : User?
}

class UserServiceImpl : UserService {
    override fun fetchAll(): List<User> = transaction { Users.selectAll().map { toUser(it) } }
    override fun fetch(id: Int) : User? = transaction { Users.select { (Users.id eq id) }.mapNotNull { toUser(it) }.singleOrNull() }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            name = row[Users.name],
            age = row[Users.age],
            dateUpdated = row[Users.dateUpdated]
        )
}