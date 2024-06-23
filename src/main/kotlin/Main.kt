
import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.myapp.ui.value.MyAppTheme
import com.myapp.ui.value.R
import database.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ui.splash.SplashScreen
import utils.Status
import java.awt.Dimension

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    refreshFirebaseToken()
    MyAppTheme {
        val scaffoldState = rememberScaffoldState()
        val snackbarCoroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(scaffoldState = scaffoldState,snackbarHost = { CustomSnackbarHost(snackbarHostState) },) {
            Navigator(screen = SplashScreen(snackbarCoroutineScope,snackbarHostState))
        }
    }

}

@Composable
fun CustomSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        // Custom snackbar layout

        val (typeString, message) = data.message.split(": ", limit = 2)
        val type = Status.valueOf(typeString) // success, error, info

        Box(
            modifier = Modifier
                .fillMaxSize() // This will make the Box take the full size of its parent
                .padding(16.dp), // Add padding around the box, optional for aesthetics
            contentAlignment = Alignment.BottomCenter, // Aligns the Snackbar in the center of the Box

        ) {
            Snackbar( modifier = Modifier.fillMaxWidth(0.5f),
                backgroundColor = when(type){
                    Status.Success -> R.color.Success
                    Status.Info -> R.color.Info
                    Status.Error -> R.color.Error
                    else -> R.color.Info
                }) {
                Box{
                    Text(modifier = Modifier.fillMaxWidth(), text =  message, style = TextStyle(
                        color = Color.White,
                        fontSize = 14.0.sp,
                        fontFamily = FontFamily(
                            Font("fonts/GoogleSans-Regular.ttf")
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp
                    )
                    )
                }
            }
        }
    }
}

fun main() = application {
    DatabaseFactory.init()
    Window(
        onCloseRequest = ::exitApplication,
        icon = painterResource("drawable/googlelogo.png"),
        title = "Universal Testing Machine",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
    ) {
        window.minimumSize = Dimension(1024, 600)
        App()
    }

}


