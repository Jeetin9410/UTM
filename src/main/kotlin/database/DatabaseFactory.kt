package database

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:sqlite:UniversalTestingMachine.db", driver = "org.sqlite.JDBC")
        // Optional: Configure a connection pool here
    }
}