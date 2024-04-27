
import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import com.myapp.ui.value.MyAppTheme
import ui.splash.SplashScreen
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
