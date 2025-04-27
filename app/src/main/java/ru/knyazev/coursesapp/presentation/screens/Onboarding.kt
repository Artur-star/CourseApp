package ru.knyazev.coursesapp.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.presentation.ui.theme.GreenButton
import ru.knyazev.coursesapp.presentation.ui.theme.LightGrayTransparent
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryButton

@Composable
fun Onboarding() {
    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(100.dp))
        TitleText()
        Spacer(Modifier.height(32.dp))
        GridButtons()
        Spacer(Modifier.weight(1f))
        ContinueButton()
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.header_onboarding),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun GridButtons() {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val containerWidth = remember { mutableStateOf(0.dp) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .horizontalScroll(scrollState)
            .onSizeChanged { size ->
                containerWidth.value = with(density) { size.width.toDp() }
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .zIndex(0f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(text = stringResource(R.string.administration_1c))
                }
                ContainerButtons(
                    onClick = {},
                    modifier = Modifier
                        .rotate(-15f)
                        .offset(y = 15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenButton),
                ) { Text(stringResource(R.string.rabbitMQ)) }
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.traffic))
                }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .zIndex(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.content_marketing))
                }
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.b2b_marketing))
                }
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.google_analytics))
                }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .zIndex(0f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.ux_researcher))
                }
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.web_analytics))
                }
                ContainerButtons(
                    onClick = {},
                    modifier = Modifier
                        .rotate(15f)
                        .offset(y = (-15).dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
                ) { Text(stringResource(R.string.big_data)) }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .zIndex(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.game_design))
                }
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.web_design))
                }
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.cinema_4D))
                }
                ContainerButtons(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(stringResource(R.string.industrial_engineering))
                }
            }
            Row(
                modifier = Modifier.zIndex(0f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.webflow))
                }
                ContainerButtons(
                    onClick = {},
                    modifier = Modifier
                        .rotate(-15f)
                        .offset(y = (-15).dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
                ) { Text(stringResource(R.string.three_js)) }
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.parsing))
                }
                ContainerButtons(onClick = {}) {
                    Text(stringResource(R.string.python_development))
                }
            }
        }
        LaunchedEffect(containerWidth) {
            scrollState.scrollTo((containerWidth.value.value / 2).toInt())
        }
    }
}

@Composable
fun ContinueButton() {
    PrimaryButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.continue_butt)
        )
    }
}

@Composable
fun ContainerButtons(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = LightGrayTransparent),
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 20.dp),
    content: @Composable RowScope.() -> Unit,
) {
    PrimaryButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = RoundedCornerShape(100.dp),
        contentPadding = contentPadding,
        content = content
    )
}

