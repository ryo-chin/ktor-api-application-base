package com.example.factory

import com.example.model.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())
        transaction {
            drop(Users)
            create(Users)
            Users.insert {
                it[name] = "hakiba"
                it[age] = 27
                it[dateUpdated] = System.currentTimeMillis()
            }
            Users.insert {
                it[name] = "baki"
                it[age] = 18
                it[dateUpdated] = System.currentTimeMillis()
            }
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://localhost:3309/hakiba_db"
        config.username = "hakiba_user"
        config.password = "password"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}