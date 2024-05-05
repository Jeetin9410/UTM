package utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.myapp.ui.value.R
import model.*


@Composable
fun IconAndTextView(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
    image: ImageVector = Icons.Default.Home,
    name: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) R.color.selectedItemBackgroundColor else Color.Transparent
    val color = if (isSelected) Color.White else Color.LightGray
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 1.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .background(backgroundColor,shape = RoundedCornerShape(8.dp)).height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Image(
            painter = painterResource(Icons.Default.Home),
            modifier = Modifier.size(16.dp),
            colorFilter = ColorFilter.tint(
                color = color
            )
        )*/
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Icon(image, "Navigate to home",
            tint = color , modifier = Modifier.size(18.dp) )
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Text(
            text = name,
            color = color,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }


    @Composable
    fun showDialog(onDismiss: () -> Unit) {
        Dialog(onCloseRequest = onDismiss, undecorated = true) {
            // Dialog content
            Card(elevation = 8.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("This is a dialog", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
