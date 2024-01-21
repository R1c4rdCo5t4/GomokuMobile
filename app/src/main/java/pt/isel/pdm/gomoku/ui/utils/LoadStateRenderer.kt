package pt.isel.pdm.gomoku.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R

@Composable
fun <T> LoadStateRenderer(
    loadState: LoadState<T>,
    pullToRefresh: (() -> Unit)? = null,
    content: @Composable (T) -> Unit = {}
) {
    when (loadState) {
        is Idle -> {}
        is Loading -> LoadingSpinner()
        is Loaded -> {
            when (loadState.value.isSuccess) {
                true -> RenderSuccess(loadState.getOrThrow(), pullToRefresh, content)
                false -> RenderFailure(pullToRefresh)
            }
        }
    }
}

@Composable
fun <T> RenderSuccess(
    value: T,
    pullToRefresh: (() -> Unit)?,
    content: @Composable (T) -> Unit = {}
) {
    if (pullToRefresh != null) {
        PullToRefresh(onRefresh = pullToRefresh) {
            content(value)
        }
    } else {
        content(value)
    }
}

@Composable
fun RenderFailure(
    pullToRefresh: (() -> Unit)?
) {
    if (pullToRefresh != null) {
        PullToRefresh(onRefresh = pullToRefresh) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.pull_to_refresh_icon),
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(10.dp)
                Text(
                    text = stringResource(R.string.pull_to_refresh),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
