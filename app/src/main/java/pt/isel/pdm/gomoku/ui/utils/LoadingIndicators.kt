package pt.isel.pdm.gomoku.ui.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pt.isel.pdm.gomoku.domain.game.Color
import kotlin.math.roundToInt

private val pieceSize = 22.dp
private const val JUMP_TICK = 150
private const val INTERVAL = 2000
private const val ANIMATION_DELAY = 25L

@Composable
fun LoadingSpinner() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun LoadingAnimatedIndicator() {
    var time by rememberSaveable { mutableStateOf(0L) }
    val pxToMove = with(LocalDensity.current) { -50.dp.toPx().roundToInt() }
    val offset1 by animateIntOffsetAsState(
        targetValue = getTimeToJump(time, 0, pxToMove),
        animationSpec = tween(JUMP_TICK, easing = LinearEasing),
        label = "offset1"
    )
    val offset2 by animateIntOffsetAsState(
        targetValue = getTimeToJump(time, JUMP_TICK / 2, pxToMove),
        animationSpec = tween(JUMP_TICK, easing = LinearEasing),
        label = "offset2"
    )
    val offset3 by animateIntOffsetAsState(
        targetValue = getTimeToJump(time, JUMP_TICK, pxToMove),
        animationSpec = tween(JUMP_TICK, easing = LinearEasing),
        label = "offset3"
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(ANIMATION_DELAY)
            time += ANIMATION_DELAY
        }
    }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.width(pieceSize * 4),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnimatedPieceView(offset1, Color.BLACK)
            AnimatedPieceView(offset2, Color.WHITE)
            AnimatedPieceView(offset3, Color.BLACK)
        }
    }
}

@Composable
fun AnimatedPieceView(offset: IntOffset, color: Color) {
    Box(
        modifier = Modifier
            .offset { offset }
            .size(pieceSize)
            .clip(RoundedCornerShape(50))
            .background(color.toRgb())
    )
}

fun getTimeToJump(time: Long, jumpInit: Int, pyToMove: Int) =
    if (time % INTERVAL in jumpInit..jumpInit + JUMP_TICK) {
        IntOffset(0, pyToMove)
    } else {
        IntOffset.Zero
    }

@Preview
@Composable
fun LoadingSpinnerPreview() {
    LoadingSpinner()
}

@Preview
@Composable
fun LoadingAnimatedIndicatorPreview() {
    LoadingAnimatedIndicator()
}
