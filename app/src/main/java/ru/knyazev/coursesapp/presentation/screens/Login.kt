package ru.knyazev.coursesapp.presentation.screens

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.presentation.ui.theme.BlueButton
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryButton
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryTextField
import ru.knyazev.coursesapp.presentation.ui.theme.StrokeGrey

@Composable
fun Login(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        var isErrorEmailState by remember { mutableStateOf(false) }
        var isEmptyPasswordState by remember { mutableStateOf(false) }

        Spacer(Modifier.height(100.dp))
        Text(text = stringResource(R.string.login), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(28.dp))
        Text(text = stringResource(R.string.email))
        Spacer(Modifier.height(8.dp))
        CheckEmailField(isError = { isErrorEmailState = it })
        Spacer(Modifier.height(11.dp))
        Text(text = stringResource(R.string.password))
        Spacer(Modifier.height(8.dp))
        CheckPasswordField(isNotEmpty = { isEmptyPasswordState = it })
        Spacer(Modifier.height(24.dp))
        InputButton(onClick, isErrorEmailState && isEmptyPasswordState)
        Spacer(Modifier.height(16.dp))
        TextLink()
        Spacer(Modifier.height(32.dp))
        HorizontalDivider(thickness = 1.dp, color = StrokeGrey)
        Spacer(Modifier.height(32.dp))
        VkAndOkButtons()
    }
}

@Composable
fun VkAndOkButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .height(40.dp)
                .padding(4.dp),
            shape = RoundedCornerShape(30.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(painter = painterResource(R.drawable.ic_vk), contentDescription = "VK")
        }
        Spacer(Modifier.width(16.dp))
        PrimaryButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .height(40.dp)
                .padding(4.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White, Color.Black),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Icon(painter = painterResource(R.drawable.ic_classmates), contentDescription = "OK")
            }
        }
    }
}

@Composable
fun TextLink() {
    Text(
        text = stringResource(R.string.no_acc_registration),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(R.string.forgot_password),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CheckEmailField(isError: (Boolean) -> Unit) {
    var textState by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf("") }

    PrimaryTextField(
        value = textState,
        onValueChange = {
            textState = it
            errorState = if (EMAIL_ADDRESS.matcher(it).matches()) "" else "Incorrect email"
            isError(errorState.isEmpty())
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        placeholder = {
            Text(
                text = stringResource(R.string.example_email),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(0.dp)
            )
        },
        isError = errorState.isNotEmpty()
    )
}

@Composable
fun CheckPasswordField(isNotEmpty: (Boolean) -> Unit) {
    var passwordState by remember { mutableStateOf("") }
    PrimaryTextField(
        value = passwordState,
        onValueChange = {
            passwordState = it
            isNotEmpty(passwordState.isNotEmpty())
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_password),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier.alpha(0.5f)
            )
        }
    )
}

@Composable
fun InputButton(onClick: () -> Unit, enabled: Boolean) {
    PrimaryButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        enabled = enabled

    ) {
        Text(
            text = stringResource(R.string.login),
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}