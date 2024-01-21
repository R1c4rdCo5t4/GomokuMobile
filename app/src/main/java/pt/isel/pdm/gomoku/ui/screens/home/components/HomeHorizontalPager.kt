package pt.isel.pdm.gomoku.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenPagerButtonsTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTabTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.homeScreenTestTag

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeHorizontalPager(
    screens: Map<ImageVector, @Composable () -> Unit>
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(homeScreenTestTag)
    ) {
        HorizontalPager(
            pageCount = screens.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            screens.values.toList()[pageIndex]()
        }
        Box {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.testTag(homeScreenPagerButtonsTestTag)
                ) {
                    screens.keys.toList().forEachIndexed { index, icon ->
                        run {
                            val selected = pagerState.currentPage == index
                            Tab(
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = stringResource(R.string.menu_bar_icon),
                                        tint = if (selected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.secondary
                                        }
                                    )
                                },
                                selected = selected,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .testTag("$homeScreenTabTestTag-$index")
                            )
                        }
                    }
                }
            }
        }
    }
}
