package utils

import dev.gitlive.firebase.FirebaseApp
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import io.ktor.util.*
import model.FirebaseSignInRequest
import networking.KtorClient

object FirebaseApp {
    lateinit var app : FirebaseApp

    @OptIn(InternalAPI::class)
    suspend fun signInWithEmailAndPassword(email: String, password: String): HttpResponse? {
        return KtorClient.client.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBJZ_zXdVg8lAv76SQxYr2NPH82MSaNLBU") {
            setBody(FirebaseSignInRequest(email, password, false))
            contentType(ContentType.Application.Json)
        }

       // return response.toString()
    }
}