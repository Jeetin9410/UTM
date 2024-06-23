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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.myapp.ui.value.R
import model.*
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image

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
}

fun loadNetworkImage(link: String): ImageBitmap {
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", stream)
    val byteArray = stream.toByteArray()

    return Image.makeFromEncoded(byteArray).asImageBitmap()
}