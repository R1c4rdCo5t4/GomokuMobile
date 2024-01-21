package pt.isel.pdm.gomoku.ui.screens.home.components.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.ui.screens.TestTags.About.aboutScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.about.components.AuthorView
import pt.isel.pdm.gomoku.ui.utils.Spacer

@Composable
fun AboutScreen(home: Home) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .testTag(aboutScreenTestTag)
    ) {
        item {
            Text(
                text = stringResource(R.string.about),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(20.dp)
            Column(
                modifier = Modifier.padding(30.dp)
            ) {
                Text(
                    text = home.description,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(80.dp)
                Text(
                    text = stringResource(R.string.developed_by),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(20.dp)
                home.authors.forEach {
                    AuthorView(it.name, it.email)
                }
                Spacer(100.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "${stringResource(R.string.version)} ${home.version}",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(Home("", "", "", emptyList(), ""))
}
