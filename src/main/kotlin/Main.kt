import android.app.Application
import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebasePlatform
import com.myapp.ui.value.MyAppTheme
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.initialize
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ui.login.LoginScreen
import ui.splash.SplashScreen
import utils.FirebaseApp.signInWithEmailAndPassword
import java.awt.Dimension

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    MyAppTheme {
        Navigator(screen = SplashScreen())
    }

}

fun main() = application {
    //initializeFirebase()
    Window(
        onCloseRequest = ::exitApplication,
        icon = painterResource("drawable/googlelogo.png"),
        title = "Universal Testing Machine",
        state = rememberWindowState(width = 1024.dp, height = 600.dp),
    ) {
        window.minimumSize = Dimension(1024, 600)
        App()
    }

}

/*fun initializeFirebase() {
    FirebasePlatform.initializeFirebasePlatform(
        object : FirebasePlatform() {
            val storage = mutableMapOf<String, String>()
            override fun store(key: String, value: String) = storage.set(key, value)
            override fun retrieve(key: String) = storage[key]
            override fun clear(key: String) {
                storage.remove(key)
            }
            override fun log(msg: String) = println(msg)
        },
    )

    val options = FirebaseOptions(
        applicationId = "1:957797432973:android:28a57ef1d3ef43311c2cc4",
        apiKey = "AIzaSyBJZ_zXdVg8lAv76SQxYr2NPH82MSaNLBU",
        projectId = "universaltestingmachine-4589e",
    )

    Firebase.initialize(Application(), options)
}*/
