package fpoly.huyndph40487.galaxyqr

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lightspark.composeqr.QrCodeView

@Composable
fun HomeScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var valueName by remember { mutableStateOf("") }
    var valuePhone by remember { mutableStateOf("") }
    var valueLinkFb by remember { mutableStateOf("") }
    var valueOther by remember { mutableStateOf("") }
    var dataQR by remember { mutableStateOf("") }
    var isSave by remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isSave) {
            getQRCodeView(dataQR) {
                isSave = false
            }
        } else {
            Row(Modifier.padding(vertical = 20.dp)) {
                Text(
                    text = "User info", modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Center
                )

                Text(text = "Save", modifier = Modifier.clickable {
                    dataQR =
                        "https://northern2001.github.io/Galaxy-QR/index.html?name=$valueName&phone=$valuePhone&fb=$valueLinkFb&other=$valueOther"
                    isSave = true
                })
            }

            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )

            getInputForm("Your name", valueName) {
                valueName = it
            }

            getInputForm("Your number phone", valuePhone) {
                valuePhone = it
            }

            getInputForm("Your link facebook", valueLinkFb) {
                valueLinkFb = it
            }

            getInputForm("Other", valueOther) {
                valueOther = it
            }
        }
    }
}

@Composable
fun getInputForm(title: String, value: String, onChangeValue: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(text = title)
        TextField(value = value, onValueChange = {
            onChangeValue(it)
        })
    }
}

@Composable
fun getQRCodeView(data: String, onClose: () -> Unit) {
    Text(
        text = "Close",
        textAlign = TextAlign.End,
        color = Color.Black.copy(0.3f),
        modifier = Modifier
            .padding(start = 250.dp)
            .clickable {
                onClose()
            })

    QrCodeView(
        data = data,
        modifier = Modifier.size(300.dp)
    )

}

