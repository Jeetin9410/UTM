package database

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import utils.DateTimeUtils
import java.sql.Connection
import java.sql.DriverManager

fun addUser(name: String, email: String,token:String,profilePic: String,loginTime: Long) {
    transaction {
        Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.token] = token
            it[Users.profilePic] = profilePic
            it[lastLoginTime] = loginTime
        }
    }
}

fun getAllUsers(): List<User> {
    return try {
        transaction {
            // Check if the Users table exists
            if (doesTableExist("Users")) {
                Users.selectAll().map {
                    User(
                        name = it[Users.name],
                        email = it[Users.email],
                        token = it[Users.token],
                        profilePic = it[Users.profilePic],
                        loginTime = it[Users.lastLoginTime]
                    )
                }
            } else {
                // Return an empty list if the table does not exist
                emptyList()
            }
        }
    } catch (e: ExposedSQLException) {
        // Handle the exception (e.g., log it, show a message to the user, etc.)
        e.printStackTrace()
        emptyList()
    }
}

fun deleteAllUsers() = transaction {
    Users.deleteAll()
}

fun refreshFirebaseToken() {
   with(getAllUsers()){
       if(size>0){
           if(DateTimeUtils.isDifferenceGreaterOrEqual24Hours(DateTimeUtils.epochTime(),this.get(0).loginTime)){
               deleteAllUsers()
           }
       }
   }
}

data class User(val name: String, val email: String, val token: String,val profilePic: String,val loginTime: Long)
fun doesTableExist(tableName: String): Boolean {
    return try {
        val connection: Connection = DriverManager.getConnection("jdbc:sqlite:UniversalTestingMachine.db")
        val metaData = connection.metaData
        val resultSet = metaData.getTables(null, null, tableName, null)
        val exists = resultSet.next()
        resultSet.close()
        connection.close()
        exists
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}