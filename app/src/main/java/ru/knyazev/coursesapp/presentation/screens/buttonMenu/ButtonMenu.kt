package ru.knyazev.coursesapp.presentation.screens.buttonMenu

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain
import ru.knyazev.coursesapp.presentation.ui.theme.LightGrayDark
import ru.knyazev.coursesapp.presentation.ui.theme.StrokeGrey
import ru.knyazev.coursesapp.presentation.ui.theme.WhiteMainDark

@Composable
fun ButtonMenu() {
    val items = listOf(
        ButtonMenuItem.MainBut,
        ButtonMenuItem.FavoriteBut,
        ButtonMenuItem.AccBut
    )
    val selectedItem = remember { mutableIntStateOf(ButtonMenuItem.MainBut.title) }

    NavigationBar(modifier = Modifier.background(color = StrokeGrey)) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.intValue == item.title,
                onClick = {
                    selectedItem.intValue = item.title
                },
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = LightGrayDark,
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Transparent,
                    unselectedIconColor = Color.Transparent,
                    unselectedTextColor = Color.Transparent,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = "",
                        tint = if (selectedItem.intValue == item.title) GreenMain else WhiteMainDark
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
                            color = if (selectedItem.intValue == item.title) GreenMain else WhiteMainDark
                        )
                    )
                }
            )
        }
    }
}