package ui.login

import android.app.Application
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebasePlatform
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.app
import com.myapp.ui.value.R
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.initialize
import initializeFirebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Async
import utils.FirebaseApp

class LoginScreen: Screen {

    @Composable
    override fun Content() {
        loginScreen()
    }

    @Preview
    @Composable
    fun loginScreen(){
        val navigator = LocalNavigator.currentOrThrow
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
                            Text("Crafted with Love ❤\uFE0F", style = TextStyle(
                                color = Color.White,
                                fontSize = 12.0.sp,
                                fontFamily = FontFamily(
                                    Font("fonts/GoogleSans-Medium.ttf")
                                )
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
                    verticalArrangement = Arrangement.Center) {
                    LoginCard()
                }
            }
        }

    }

    @Composable
    fun LoginCard() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        Card(
            backgroundColor = Color.White,
            modifier = Modifier.padding(60.dp).fillMaxWidth(),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium // Default rounded shape
        ) {
            Column(
                modifier = Modifier.padding(36.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome to UTM!", style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.0.sp,
                    fontFamily = FontFamily(
                        Font("fonts/GoogleSans-Medium.ttf")
                    )
                ))
                Spacer(modifier = Modifier.height(10.dp))
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

                        runBlocking{
                            Firebase.auth.signInWithEmailAndPassword("jeetinn@gmail.com", "123456").let {
                                println(it.user?.email ?: "fnkdsj")
                            }
                        }
                        // Replace with actual login logic
                    })
                )
                Button(
                    onClick = {
                        println("Login clicked with email: $email, Password: $password") // Replace with actual login logic
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}