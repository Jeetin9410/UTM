package database

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

fun addUser(name: String, email: String) {
    transaction {
        Users.insert {
            it[Users.name] = name
            it[Users.email] = email
        }
    }
}

fun getAllUsers(): List<User> = transaction {
    Users.selectAll().map {
        User(
            name = it[Users.name],
            email = it[Users.email]
        )
    }
}

data class User(val name: String, val email: String)
