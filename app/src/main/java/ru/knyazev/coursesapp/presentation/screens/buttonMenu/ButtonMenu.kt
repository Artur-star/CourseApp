package ru.knyazev.coursesapp.presentation.screens.buttonMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain

@Composable
fun ButtonMenu(
    currentScreen: String,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit,
    onAccountClick: () -> Unit,
) {
    val items = listOf(
        ButtonMenuItem.HomeBut,
        ButtonMenuItem.FavoriteBut,
        ButtonMenuItem.AccBut
    )
    val selectedItem = remember { mutableStateOf(currentScreen) }
    Column {
        HorizontalDivider(thickness = 1.5.dp, color = MaterialTheme.colorScheme.secondary)
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            windowInsets = WindowInsets(0.dp),
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = selectedItem.value == item.route,
                    onClick = {
                        selectedItem.value = item.route
                        when (item.title) {
                            ButtonMenuItem.HomeBut.title -> onHomeClick()
                            ButtonMenuItem.FavoriteBut.title -> onFavoritesClick()
                            ButtonMenuItem.AccBut.title -> onAccountClick()
                        }
                    },
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                        selectedIconColor = Color.Transparent,
                        selectedTextColor = Color.Transparent,
                        unselectedIconColor = Color.Transparent,
                        unselectedTextColor = Color.Transparent,
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,

                        ),
                    icon = {
                        Icon(
                            painter = painterResource(item.iconId),
                            contentDescription = "",
                            tint = if (selectedItem.value == item.route) GreenMain else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.title),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = if (selectedItem.value == item.route) GreenMain else MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                )
            }
        }
    }
}