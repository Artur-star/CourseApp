package ru.knyazev.coursesapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.domain.model.CourseUI
import ru.knyazev.coursesapp.presentation.screens.buttonMenu.ButtonMenu
import ru.knyazev.coursesapp.presentation.screens.buttonMenu.ButtonMenuItem
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain
import ru.knyazev.coursesapp.presentation.ui.theme.WhiteMainDark
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val homeState = viewModel.homeState.collectAsState()
    val favoriteState = viewModel.favoritesState.collectAsState()
    val currentScreen = remember { mutableStateOf(ButtonMenuItem.HomeBut.route) }

    Scaffold(
        bottomBar = {
            ButtonMenu(
                currentScreen = currentScreen.value,
                onHomeClick = {
                    currentScreen.value = ButtonMenuItem.HomeBut.route
                },
                onFavoritesClick = {
                    currentScreen.value = ButtonMenuItem.FavoriteBut.route
                },
                onAccountClick = {
                    currentScreen.value = ButtonMenuItem.AccBut.route
                })
        })
    { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (currentScreen.value) {
                ButtonMenuItem.HomeBut.route -> HomeScreen(homeState.value.listCourses, viewModel)
                ButtonMenuItem.FavoriteBut.route -> FavoritesScreen(
                    favoriteState.value.listCourses,
                    viewModel
                )

                ButtonMenuItem.AccBut.route -> AccountScreen()
            }
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

    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxSize(),
    ) {
        FieldSearchCourse()
        Spacer(Modifier.height(16.dp))
        SortedBut {
            viewModel.sortCoursesByPublishDate()
        }
        Spacer(Modifier.height(16.dp))
        ListCourses(list, viewModel)
    }
}

@Composable
fun ListCourses(list: List<CourseUI>, viewModel: MainViewModel) {

    LazyColumn(modifier = Modifier.fillMaxWidth(), state = rememberLazyListState()) {
        items(list) { item ->
            key (item.id) {
                CourseItem(item, viewModel)
            }
        }
    }
}

@Composable
fun CourseItem(item: CourseUI, viewModel: MainViewModel) {
    val title = item.title
    val text = item.text
    val price = item.price
    val countLines = remember { mutableStateOf(true) }

    Column(
        Modifier.background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        ItemHeader(item) {
            viewModel.updateCourseToFavorites(item)
        }
        Column(Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Text(
                text = text,
                maxLines = if (countLines.value) 2 else Int.MAX_VALUE,
                overflow = if (countLines.value) TextOverflow.Ellipsis else TextOverflow.Clip,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                modifier = Modifier.alpha(0.7f)
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "$price \u20BD", style = MaterialTheme.typography.titleMedium)
                Box {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(
                                onClick = {
                                    countLines.value = !countLines.value
                                }),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Подробнее",
                            color = GreenMain,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_cursor),
                            contentDescription = "Cursor",
                            tint = GreenMain,
                        )
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun ItemHeader(item: CourseUI, onFavClick: () -> Unit) {
    val startDateParseState = remember(item.startDate) { parseDate(item.startDate) }

    Box(
        Modifier
            .fillMaxWidth()
            .height(114.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.cover),
            contentDescription = "",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxSize(),
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
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = false)
                    ) {
                        onFavClick()
                    }
            ) {
                if (item.hasLike) {
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
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    )
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
                Text(
                    text = item.rate,
                    style = MaterialTheme.typography.bodySmall.copy(color = WhiteMainDark)
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = startDateParseState.toString(),
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall.copy(color = WhiteMainDark)
            )
        }
    }
}

@Composable
fun SortedBut(onSort: () -> Unit) {
    Box(
        Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .clickable(
                    onClick = onSort,
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_added),
                color = GreenMain
            )
            Image(
                modifier = Modifier
                    .padding(0.dp),
                painter = painterResource(R.drawable.ic_arrow_down_up),
                contentDescription = "",
            )
        }
    }
}

@Composable
fun FieldSearchCourse() {
    var textState by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = textState,
            onValueChange = {
                textState = it
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(28.dp))
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(16.dp))
                    Box(Modifier.weight(1f)) {
                        if (textState.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_course),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                                modifier = Modifier.alpha(0.5f)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_funnel),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
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

private fun parseDate(dateString: String): String? {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru", "RU"))

    return try {
        val date = LocalDate.parse(dateString, inputFormatter)
        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        null
    }
}