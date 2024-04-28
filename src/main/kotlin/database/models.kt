package database

import org.jetbrains.exposed.sql.Table


object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val email = varchar("email", 100)
    val token = varchar("token",1000)
    val profilePic = varchar("profilePic",1000)

    override val primaryKey = PrimaryKey(id)
}