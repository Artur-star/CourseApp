package ru.knyazev.coursesapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.presentation.screens.buttonMenu.ButtonMenu
import ru.knyazev.coursesapp.presentation.ui.theme.DarkGrayDark
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain
import ru.knyazev.coursesapp.presentation.ui.theme.LightGrayLight
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryTextField
import ru.knyazev.coursesapp.presentation.ui.theme.WhiteMainDark

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = { ButtonMenu() })
    { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            ContentMainScreen()
        }
    }
}

@Composable
fun ContentMainScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        FieldSearchCourse()
        Spacer(Modifier.height(16.dp))
        SortedBut()
        Spacer(Modifier.height(16.dp))
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
                modifier = Modifier.clickable(
                    onClick = {},

                    ),
                painter = painterResource(R.drawable.ic_funnel),
                contentDescription = "",
                tint = WhiteMainDark,
            )
        }
    }
}