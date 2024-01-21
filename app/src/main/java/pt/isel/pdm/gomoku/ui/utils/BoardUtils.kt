package pt.isel.pdm.gomoku.ui.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.ui.theme.InverseSurface

@Composable
fun Cross() {
    Divider(Modifier, 2.dp, InverseSurface)
    Divider(Modifier.fillMaxHeight().width(2.dp), 2.dp, InverseSurface)
}

@Composable
fun BoxScope.Corner(horizontalAlignment: Alignment, verticalAlignment: Alignment) {
    apply {
        Divider(
            modifier = Modifier.fillMaxWidth(0.5f).align(horizontalAlignment),
            thickness = 2.dp,
            color = InverseSurface
        )
        Divider(
            modifier = Modifier.fillMaxHeight(0.5f).align(verticalAlignment).width(2.dp),
            thickness = 2.dp,
            color = InverseSurface
        )
    }
}

@Composable
fun BoxScope.TopLeftCorner() {
    Corner(Alignment.CenterEnd, Alignment.BottomCenter)
}

@Composable
fun BoxScope.TopRightCorner() {
    Corner(Alignment.CenterStart, Alignment.BottomCenter)
}

@Composable
fun BoxScope.BottomLeftCorner() {
    Corner(Alignment.CenterEnd, Alignment.TopCenter)
}

@Composable
fun BoxScope.BottomRightCorner() {
    Corner(Alignment.CenterStart, Alignment.TopCenter)
}

@Composable
fun BoxScope.HorizontalLine(row: Int) {
    apply {
        Divider(
            thickness = 2.dp,
            color = InverseSurface
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .align(if (row == 1) Alignment.BottomCenter else Alignment.TopCenter)
                .width(2.dp),
            thickness = 2.dp,
            color = InverseSurface
        )
    }
}

@Composable
fun BoxScope.VerticalLine(col: Int) {
    apply {
        Divider(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(if (col == 1) Alignment.CenterEnd else Alignment.CenterStart),
            thickness = 2.dp,
            color = InverseSurface
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            thickness = 2.dp,
            color = InverseSurface
        )
    }
}
