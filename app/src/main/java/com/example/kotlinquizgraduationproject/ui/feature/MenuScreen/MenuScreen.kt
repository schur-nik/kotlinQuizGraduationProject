package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import com.example.kotlinquizgraduationproject.utils.updateLocale

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    MenuScreen(
        navHostController = navController,
        MenuViewModel()
    )
}

@Composable
fun MenuScreen(
    navHostController: NavHostController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    var showSettingsDialog by remember { mutableStateOf(false) }
    var isEnglish by remember { mutableStateOf(SharedPreferencesRepository.getLanguage() == "en") }
    val context = LocalContext.current

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main_background),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                if (isSystemInDarkTheme()) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = stringResource(R.string.greating),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 60.dp)
                            .padding(top = 50.dp)
                            .height(250.dp),
                        fontSize = 45.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 45.sp,
                        textAlign = TextAlign.Center,
                    )
                    MenuButton(stringResource(R.string.menubutton_levels), { navHostController.navigate(Routes.LevelsScreen.route) }, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_profile), {}, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_settings), { showSettingsDialog = true }, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_exit), { exitApp(context) }, Modifier.align(Alignment.CenterHorizontally))
                }
            }
            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                    isEnglish = isEnglish,
                    onToggleLanguage = { newIsEnglish -> isEnglish = newIsEnglish }
                )
            }
            Text(text = "USER_ID = "+SharedPreferencesRepository.getUserId().toString(), modifier = Modifier.padding(top = 15.dp))
            Text(text = "LOCALE = "+SharedPreferencesRepository.getLanguage())
            Text(text = "isFirstLaunch = "+SharedPreferencesRepository.isFirstLaunch(), modifier = Modifier.padding(top = 30.dp))
        }
    )

}

@Composable
private fun MenuButton(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(250.dp)
            .height(70.dp),
        contentPadding = PaddingValues(7.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent,
            Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.main_blue),
                            Color.Black
                        ),
                        startY = 0f,
                        endY = 260f
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(name, fontSize = 24.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun SettingsDialog(onDismiss: () -> Unit, isEnglish: Boolean, onToggleLanguage: (Boolean) -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 200.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = onDismiss, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                            )
                        }
                        Text(
                            text = "Settings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Select Language:")
                    Text(text = "RU")
                    Switch(
                        checked = isEnglish,
                        onCheckedChange = { isChecked ->
                            onToggleLanguage(isChecked)
                            val language = if (isChecked) "en" else "ru"
                            SharedPreferencesRepository.setLanguage(language)
                            updateLocale(context, language)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(id = R.color.main_blue),
                            uncheckedThumbColor = colorResource(id = R.color.main_blue),
                            checkedTrackColor = Color.LightGray, // Цвет дорожки при включенном состоянии
                            uncheckedTrackColor = Color.LightGray // Цвет дорожки при выключенном состоянии
                        )
                    )
                    Text(text = "ENG")
                }
            }
        }
    }
}

private fun exitApp(context: Context) {
    (context as? Activity)?.finish()
}

@Composable
fun SplashImage() {
}