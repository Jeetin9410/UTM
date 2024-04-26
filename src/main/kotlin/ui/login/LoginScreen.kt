package ui.login

import android.app.Application
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.gson.Gson
import com.myapp.ui.value.R
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.*
import model.FirebaseLoginErrorResp
import org.jetbrains.annotations.Async
import utils.FirebaseApp
import utils.Status

class LoginScreen: Screen {

    @Composable
    override fun Content() {

        loginScreen()
    }

    @Preview
    @Composable
    fun loginScreen(){
        val navigator = LocalNavigator.currentOrThrow
        val scaffoldState = rememberScaffoldState()
        val snackbarCoroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(scaffoldState = scaffoldState,snackbarHost = { CustomSnackbarHost(snackbarHostState) },) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Left panel
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(R.color.Elephant)
                        .fillMaxSize()
                ) {
                    Column (modifier = Modifier.matchParentSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            Column(modifier = Modifier.matchParentSize() ,verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("We are", style = TextStyle(
                                    color = Color.White,
                                    fontSize = 25.0.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Bold.ttf")
                                    )))
                            }

                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            Column(modifier = Modifier.matchParentSize() ,verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("PROUD", style = TextStyle(
                                    color = Color.White,
                                    fontSize = 38.0.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Bold.ttf")
                                    )
                                )
                                )
                                Text("PARTNERS", style = TextStyle(
                                    color = Color.White,
                                    fontSize = 27.0.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Bold.ttf")
                                    )
                                ))
                                Text("TO GREAT",style = TextStyle(
                                    color = Color.White,
                                    fontSize = 29.0.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Bold.ttf")
                                    )
                                ))
                                Text("BRANDS",style = TextStyle(
                                    color = Color.White,
                                    fontSize = 34.0.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Bold.ttf")
                                    )
                                ))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            Column(modifier = Modifier.matchParentSize() ,verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource("drawable/googlelogo.png"),
                                    modifier = Modifier.size(80.dp),
                                    contentDescription = "Logo"
                                )
                                Text("Crafted with Love ❤\uFE0F\nVersion v1.0.0", style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.0.sp,
                                    fontFamily = FontFamily(
                                        Font("fonts/GoogleSans-Medium.ttf")
                                    ),
                                    textAlign = TextAlign.Center
                                ))
                            }
                        }


                    }
                }

                // Right panel
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Column(modifier = Modifier.matchParentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(modifier = Modifier.padding(top = 50.dp), text = "Welcome to",style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.0.sp,
                            fontFamily = FontFamily(
                                Font("fonts/GoogleSans-Medium.ttf")
                            )
                        ))
                        Text("Universal Testing Machine",style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.0.sp,
                            fontFamily = FontFamily(
                                Font("fonts/GoogleSans-Medium.ttf")
                            ),
                            fontWeight = FontWeight.Bold
                        ))
                        LoginCard(snackbarCoroutineScope,scaffoldState,snackbarHostState)
                    }
                }
            }
        }



    }

    @Composable
    fun LoginCard(snackbarCoroutineScope : CoroutineScope, scaffoldState : ScaffoldState, snackbarHostState: SnackbarHostState  ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var btnClick by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var loading by remember { mutableStateOf(false) }


        Card(
            backgroundColor = Color.White,
            modifier = Modifier.padding(60.dp).fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp) // Default rounded shape
        ) {
            Column(
                modifier = Modifier.padding(36.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("LOGIN", style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.0.sp,
                    fontFamily = FontFamily(
                        Font("fonts/GoogleSans-Medium.ttf")
                    )
                ))
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email",style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.0.sp,
                        fontFamily = FontFamily(
                            Font("fonts/GoogleSans-Regular.ttf")
                        )
                    ))},
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedBorderColor = R.color.PictonBlue,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth().height(60.0.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.0.sp,
                        fontFamily = FontFamily(
                            Font("fonts/GoogleSans-Regular.ttf")
                        )
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password",style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.0.sp,
                        fontFamily = FontFamily(
                            Font("fonts/GoogleSans-Regular.ttf")
                        )
                    )) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedBorderColor = R.color.PictonBlue,
                        unfocusedBorderColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = R.color.PictonBlue
                    ),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisibility)
                            Icons.Default.Clear
                        else
                            Icons.Default.Share
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(icon, "Toggle password visibility",
                              tint = Color.LightGray  )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(60.0.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.0.sp,
                        fontFamily = FontFamily(
                            Font("fonts/GoogleSans-Regular.ttf")
                        )
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {

                    })
                )
                Button( enabled = !loading
                    ,onClick = {
                        if(email.isBlank() && password.isBlank()){
                            snackbarCoroutineScope.launch {
                                snackbarHostState.showSnackbar("Error: Email and Password fields are blank!")
                            }
                            return@Button
                        }else{
                            if(email.isBlank()){
                                snackbarCoroutineScope.launch {
                                    snackbarHostState.showSnackbar("Error: Email field is blank!")
                                }
                                return@Button
                            }else if(password.isBlank()){
                                snackbarCoroutineScope.launch {
                                    snackbarHostState.showSnackbar("Error: Password field is blank!")
                                }
                                return@Button
                            }
                        }
                        loading = true
                        coroutineScope.launch {
                            val token = FirebaseApp.signInWithEmailAndPassword(email, password)?.let {
                                if(it.status == HttpStatusCode.OK){
                                    println(it.body() as String)
                                    btnClick = true
                                    snackbarCoroutineScope.launch {
                                        snackbarHostState.showSnackbar("Success: Login Successful!")
                                    }
                                }else{
                                    println(it.body() as String)
                                   val error: FirebaseLoginErrorResp =  Gson().fromJson(it.body() as String, FirebaseLoginErrorResp::class.java)

                                    snackbarCoroutineScope.launch {
                                        snackbarHostState.showSnackbar("Error: Login Failed ${error.error.message}")
                                    }
                                }
                                loading = false
                            }
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                if (loading){
                    LinearProgressIndicator(color = R.color.PictonBlue)
                }
                Spacer(modifier = Modifier.height(10.dp))
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
                        ))
                    }
                }
            }
        }
    }
}