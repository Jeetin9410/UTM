package model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseSignInRequest(val email: String, val password: String, val returnSecureToken: Boolean)

@kotlinx.serialization.Serializable
data class FirebaseResponse(val idToken: String, val email: String)