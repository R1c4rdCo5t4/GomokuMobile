package pt.isel.pdm.gomoku.ui.screens.home.components.about.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorView(name: String, email: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val context = LocalContext.current
        val intent = Intent(Intent.ACTION_SENDTO).apply { data = Uri.parse("mailto:$email") }
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)) {
                append(name)
                addStringAnnotation(
                    tag = "EMAIL_TAG",
                    annotation = name,
                    start = 0,
                    end = name.length
                )
            }
        }
        ClickableText(
            text = annotatedString,
            modifier = Modifier.padding(5.dp)
        ) {
            context.startActivity(intent)
        }
    }
}
