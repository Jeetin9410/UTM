package networking


import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorClient {

    val client = HttpClient(OkHttp) {
        defaultRequest {
            header("Content-Type", "application/json")
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    suspend inline fun <reified T> HttpResponse.toResponse(): Resource<T> {
        return when (status.value) {
            in 200..226 -> Resource.success(body())

            in 300..399 -> Resource.error(
                Failure.HttpErrorRedirectResponseException(
                    RedirectResponseException(this, "Redirect response exception.")
                )
            )
            in 400..499 -> Resource.error(
                Failure.HttpErrorRedirectResponseException(
                    RedirectResponseException(this, "Redirect response exception.")
                )
            )
            400 -> Resource.error(
                Failure.HttpErrorBadRequest(
                    ClientRequestException(this, "Bad Request response exception.")
                )
            )
            401 -> Resource.error(
                Failure.HttpErrorUnauthorized(
                    ClientRequestException(this, "Bad Request response exception.")
                )
            )
            403 -> Resource.error(
                Failure.HttpErrorForbidden(
                    ClientRequestException(this, "Forbidden Exception due to roles.")
                )
            )
            404 -> Resource.error(
                Failure.HttpErrorNotFound(
                    ClientRequestException(this, "End point not found.")
                )
            )
            in 500..599 -> Resource.error(
                Failure.HttpErrorInternalServer(
                    ServerResponseException(this, "Internal server error.")
                )
            )
            else -> {
                Resource.error(
                    Failure.HttpErrorInternalServer(
                        ServerResponseException(this, "Internal server error.")
                    )
                )
            }
        }
    }
}