package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import android.app.Activity
import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

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
    var showProfileDialog by remember { mutableStateOf(false) }
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
                        text = stringResource(R.string.menu_greating),
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
                    MenuButton(stringResource(R.string.menubutton_profile), {showProfileDialog = true}, Modifier.align(Alignment.CenterHorizontally))
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

            if (showProfileDialog) {
                ProfileScreen(onDismiss = { showProfileDialog = false })
            }
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
fun ProfileScreen(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userEmail by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var isSignedIn by remember { mutableStateOf(false) }

    val currentUser  = auth.currentUser
    if (currentUser  != null) {
        userEmail = currentUser .email ?: ""
        userId = currentUser .uid
        isSignedIn = true
    }

    LaunchedEffect(Unit) {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                userEmail = user.email ?: ""
                userId = user.uid
                isSignedIn = true
            } else {
                userEmail = ""
                userId = ""
                isSignedIn = false
            }
        }
    }

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
                            text = stringResource(R.string.menu_profile),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = userEmail, fontSize = 14.sp, textAlign = TextAlign.Center, modifier= Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isSignedIn) {
                        Button(onClick = {
                            auth.signOut()
                            isSignedIn = false
                            userEmail = ""
                            userId = ""
                        }) {
                            Text(text = stringResource(R.string.menu_log_out),
                                 modifier = Modifier.width(150.dp),
                                 textAlign = TextAlign.Center)
                        }
                    } else {
                        Button(onClick = { signInWithGoogle(context, auth) }
                        ) {
                            Text(text = stringResource(R.string.menu_sign_in_google_account),
                                 modifier = Modifier.width(150.dp),
                                 textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

private fun signInWithGoogle(context: Context, auth: FirebaseAuth) {
    val RC_SIGN_IN = 9001;
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val signInIntent = googleSignInClient.signInIntent
    (context as Activity).startActivityForResult(signInIntent, RC_SIGN_IN)
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
                            text = stringResource(R.string.menu_settings),
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
                    Text(text = stringResource(R.string.menu_select_language))
                    Text(text = stringResource(R.string.menu_ru))
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
                            checkedTrackColor = Color.LightGray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                    Text(text = stringResource(R.string.menu_eng))
                }
            }
        }
    }
}


private fun exitApp(context: Context) {
    (context as? Activity)?.finish()
}