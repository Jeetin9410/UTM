package ui.splash

import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapp.ui.value.R
import database.User
import database.getAllUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ui.dashboard.DashboardScreen
import ui.login.LoginScreen

class SplashScreen(private val snackbarCoroutineScope : CoroutineScope,private val snackbarHostState: SnackbarHostState) : Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column  (
            modifier = Modifier.fillMaxSize().background(R.color.BigStone),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource("drawable/googlelogo.png"),
                modifier = Modifier.size(200.dp),
                contentDescription = "Logo"
            )
            CircularProgressIndicator(color = Color.White)
        }.apply {
            LaunchedEffect(true) {
                with(getAllUsers()){
                    if(size > 0 && !this[0].token.isNullOrBlank()){
                        delay(1500)
                        navigator.push(LoginScreen(snackbarCoroutineScope,snackbarHostState))
                    }else{
                        delay(1500)
                        navigator.push(LoginScreen(snackbarCoroutineScope, snackbarHostState))
                    }
                }

            }
        }
    }
}