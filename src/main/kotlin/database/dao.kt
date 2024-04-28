package database

import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

fun addUser(name: String, email: String,token:String,profilePic: String) {
    transaction {
        Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.token] = token
            it[Users.profilePic] = profilePic
        }
    }
}

fun getAllUsers(): List<User> = transaction {
    Users.selectAll().map {
        User(
            name = it[Users.name],
            email = it[Users.email],
            token = it[Users.token],
            profilePic = it[Users.profilePic]
        )
    }
}

fun deleteAllUsers() = transaction {
    Users.deleteAll()
}

data class User(val name: String, val email: String, val token: String,val profilePic: String)
