package model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseSignInRequest(val email: String, val password: String, val returnSecureToken: Boolean)

@Serializable
data class FirebaseResponse(val idToken: String, val email: String)

@Serializable
data class FirebaseLoginErrorResp(
    val error: Error
)

@Serializable
data class Error(
    val code: Int,
    val errors: List<ErrorX>,
    val message: String
)

@Serializable
data class ErrorX(
    val domain: String,
    val message: String,
    val reason: String
)