package ru.knyazev.coursesapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.domain.model.CourseUI
import ru.knyazev.coursesapp.presentation.screens.buttonMenu.ButtonMenu
import ru.knyazev.coursesapp.presentation.screens.buttonMenu.ButtonMenuItem
import ru.knyazev.coursesapp.presentation.ui.theme.DarkGrayDark
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain
import ru.knyazev.coursesapp.presentation.ui.theme.LightGrayLight
import ru.knyazev.coursesapp.presentation.ui.theme.LightGrayTransparent
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryTextField
import ru.knyazev.coursesapp.presentation.ui.theme.WhiteMainDark

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val state = viewModel.state.collectAsState()
    Scaffold(
        bottomBar = {
            ButtonMenu(
                onHomeClick = {
                    navController.navigate(ButtonMenuItem.HomeBut.route)
                },
                onFavoritesClick = {
                    navController.navigate(ButtonMenuItem.FavoriteBut.route)
                },
                onAccountClick = {
                    navController.navigate(ButtonMenuItem.AccBut.route)
                })
        })
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ButtonMenuItem.HomeBut.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ButtonMenuItem.HomeBut.route) {
                HomeScreen(
                    state.value.listCourses,
                    viewModel
                )
            }
            composable(ButtonMenuItem.FavoriteBut.route) {
                FavoritesScreen(
                    state.value.listCourses,
                    viewModel
                )
            }
            composable(ButtonMenuItem.AccBut.route) { }
        }
    }
}

@Composable
fun FavoritesScreen(list: List<CourseUI>, viewModel: MainViewModel) {
    LaunchedEffect(viewModel) {
        viewModel.getAllCoursesFromCache()
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.favorites),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(Modifier.height(16.dp))
        ListCourses(list, viewModel)
    }
}


@Composable
fun HomeScreen(list: List<CourseUI>, viewModel: MainViewModel) {
    LaunchedEffect(viewModel) {
        viewModel.getAllCoursesFromApi()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        FieldSearchCourse()
        Spacer(Modifier.height(16.dp))
        SortedBut()
        Spacer(Modifier.height(16.dp))
        ListCourses(list, viewModel)
    }
}

@Composable
fun ListCourses(list: List<CourseUI>, viewModel: MainViewModel) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(list) { item ->
            CourseItem(item) {
                viewModel.updateCourseToFavorites(item)
            }
        }
    }
}

@Composable
fun CourseItem(item: CourseUI, onFavClick: () -> Unit) {
    val title = item.title
    val text = item.text
    val price = item.price
    val rate = item.rate
    val startDate = item.startDate
    val hasLike = item.hasLike
    val publishDate = item.publishDate


    Column(Modifier.background(color = DarkGrayDark, shape = RoundedCornerShape(16.dp))) {
        ItemHeader(rate, startDate, hasLike) {
            onFavClick()
        }
        Spacer(Modifier.height(16.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        Text(
            text = text,
            maxLines = 2,
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
            modifier = Modifier.alpha(0.7f)
        )
        Spacer(Modifier.height(10.dp))
        Row {
            Text(text = "$price \u20BD", style = MaterialTheme.typography.titleMedium)
        }
    }
    Spacer(Modifier.height(16.dp))

}

@Composable
fun ItemHeader(rate: String, startDate: String, hasLike: Boolean, onFavClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(114.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.cover),
            contentDescription = "",
            modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Box(
            Modifier
                .size(44.dp)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onFavClick()
                    }
                    .align(Alignment.Center)
                    .background(LightGrayTransparent, CircleShape)

            ) {
                if (hasLike) {
                    BaseIcon(
                        painter = painterResource(R.drawable.ic_green_bookmark),
                        tint = GreenMain,
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                    )
                } else BaseIcon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    tint = WhiteMainDark,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Center),
                )

            }
        }

        Row(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(LightGrayTransparent, RoundedCornerShape(12.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star_fill),
                    contentDescription = null,
                    tint = GreenMain
                )
                Spacer(Modifier.width(4.dp))
                Text(text = rate, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = startDate,
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = LightGrayTransparent
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun SortedBut() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.date_added),
            color = GreenMain
        )
        Image(
            modifier = Modifier
                .padding(0.dp)
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ),
            painter = painterResource(R.drawable.ic_arrow_down_up),
            contentDescription = "",
        )
    }
}

@Composable
fun FieldSearchCourse() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = stringResource(R.string.search_course),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.alpha(0.5f)
                )
            },
            color = TextFieldDefaults.colors(
                unfocusedContainerColor = if (isSystemInDarkTheme()) DarkGrayDark else LightGrayLight,
                focusedContainerColor = if (isSystemInDarkTheme()) DarkGrayDark else LightGrayLight,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "",
                    tint = WhiteMainDark
                )
            },
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = {},
            modifier = Modifier
                .clip(CircleShape)
                .background(DarkGrayDark)

        ) {
            Icon(
                painter = painterResource(R.drawable.ic_funnel),
                contentDescription = "",
                tint = WhiteMainDark,
            )
        }
    }
}

@Composable
fun BaseIcon(tint: Color, painter: Painter, modifier: Modifier) {
    Icon(
        painter = painter,
        contentDescription = "bookmark",
        tint = tint,
        modifier = modifier
    )
}