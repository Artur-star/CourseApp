package ru.knyazev.coursesapp.presentation.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.knyazev.coursesapp.R
import ru.knyazev.coursesapp.presentation.ui.theme.BlueButton
import ru.knyazev.coursesapp.presentation.ui.theme.DarkOrangeButton
import ru.knyazev.coursesapp.presentation.ui.theme.GreenMain
import ru.knyazev.coursesapp.presentation.ui.theme.LightOrangeButton
import ru.knyazev.coursesapp.presentation.ui.theme.PrimaryButton
import ru.knyazev.coursesapp.presentation.ui.theme.WhiteDark
import ru.knyazev.coursesapp.utils.Constants.OK_URI
import ru.knyazev.coursesapp.utils.Constants.VK_URI

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
        InputButton(
            onClick = onClick,
            isErrorEmailState && isEmptyPasswordState
        )
        Spacer(Modifier.height(16.dp))
        TextLink()
        Spacer(Modifier.height(32.dp))
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)
        Spacer(Modifier.height(32.dp))
        VkAndOkButtons()
    }
}

@Composable
fun VkAndOkButtons() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VK_URI))
                context.startActivity(intent)
            },
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
            Icon(
                painter = painterResource(R.drawable.ic_vk),
                contentDescription = "VK",
                tint = WhiteDark
            )
        }
        Spacer(Modifier.width(16.dp))
        PrimaryButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(OK_URI))
                context.startActivity(intent)
            },
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(LightOrangeButton, DarkOrangeButton),
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_classmates),
                    contentDescription = "OK",
                    tint = WhiteDark
                )
            }
        }
    }
}

@Composable
fun TextLink() {
    Text(
        text = buildAnnotatedString {
            append("Нету аккаунта? ")
            withStyle(SpanStyle(GreenMain)) { append("Регистрация") }
        },
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.forgot_password),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        ),
        color = GreenMain,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CheckEmailField(isError: (Boolean) -> Unit) {
    var textState by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf(false) }

    BasicTextField(
        value = textState,
        onValueChange = {
            textState = it
            errorState = EMAIL_ADDRESS.matcher(it).matches()
            isError(errorState)
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
                    .padding(horizontal = 16.dp, vertical = 11.dp)
            ) {
                if (textState.isEmpty()) {
                    Text(
                        text = stringResource(R.string.example_email),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                        modifier = Modifier.alpha(0.5f),
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun CheckPasswordField(isNotEmpty: (Boolean) -> Unit) {
    var passwordState by remember { mutableStateOf("") }

    BasicTextField(
        value = passwordState,
        onValueChange = {
            passwordState = it
            isNotEmpty(passwordState.isNotEmpty())
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
                    .padding(horizontal = 16.dp, vertical = 11.dp)
            ) {
                if (passwordState.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_password),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                        modifier = Modifier.alpha(0.5f),
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun InputButton(onClick: () -> Unit, enabled: Boolean) {
    PrimaryButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled

    ) {
        Text(
            text = stringResource(R.string.login),
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}