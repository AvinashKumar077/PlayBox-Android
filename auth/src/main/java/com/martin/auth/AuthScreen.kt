package com.martin.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.martin.core.db.LoginRequestModel
import com.martin.core.db.SignUpRequest
import com.martin.core.utils.extensions.debounceClickable
import com.martin.core.utils.extensions.noRippleClickable
import com.martin.core.utils.extensions.toasty


@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onBackToLogin: () -> Boolean,
    viewModel: AuthViewModel = hiltViewModel()
)
{
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val offsetY = with(LocalDensity.current) { (screenWidthDp * 9f / 16f).toPx() * 0.8f }

    var avatarUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var coverUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Cover image at the top
        CoverImagePickerBox(
            imageUri = coverUri,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            onImageSelected = { coverUri = it }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = with(LocalDensity.current) { offsetY.toDp() + 80.dp }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            AuthTextField(value = fullName, onValueChange = { fullName = it }, label = "Full Name")
            AuthTextField(value = email, onValueChange = { email = it }, label = "Email")
            AuthTextField(value = username, onValueChange = { username = it }, label = "Username")
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val signUpRequest = SignUpRequest(
                        fullName = fullName,
                        email = email,
                        password = password,
                        username = username,
                        avatarUri = avatarUri,
                        coverImageUri = coverUri
                    )
                    viewModel.signUp(signUpRequest)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff00b4d8),
                    contentColor = Color.White
                )
            ) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Already have an account?")
                    }
                    withStyle(style = SpanStyle(color = Color(0xff00b4d8))) {
                        append(" LogIn")
                    }
                },
                modifier = Modifier.noRippleClickable(onClick = {
                    onBackToLogin()
                    context.toasty("LogIn Clicked")
                })
            )
        }

        // Avatar floating above
        AvatarImagePickerBox(
            imageUri = avatarUri,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopCenter)
                .offset(y = with(LocalDensity.current) { offsetY.toDp() })
                .zIndex(1f),
            onImageSelected = { avatarUri = it }
        )
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AuthTextField(value = email, onValueChange = { email = it }, label = "Email")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )
        Button(
            onClick = {
                val loginRequest = LoginRequestModel(email = email, password = password)
                authViewModel.login(loginRequest)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text("Login")
        }
        Button(
            onClick = { onSignupClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text("Sign Up")
        }
    }
}


@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xff00b4d8),
            unfocusedBorderColor = Color(0xff90e0ef),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedLabelColor = Color(0xff00b4d8),
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Black
        )
    )
}


@Composable
fun CoverImagePickerBox(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    onImageSelected: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let(onImageSelected) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .debounceClickable(
                onClick = {
                    launcher.launch("image/*")
                },
                withoutRipple = false
            )
            .background(Color(0xff90e0ef)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Cover Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Placeholder Avatar",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
                Text("Click Here to Select Cover Image", color = Color.Gray)
            }
        }
    }
}

@Composable
fun AvatarImagePickerBox(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    onImageSelected: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let(onImageSelected) }

    Box(
        modifier = modifier
            .size(110.dp)
            .border(4.dp, Color.White, CircleShape)
            .padding(4.dp)
            .clip(CircleShape)
            .debounceClickable(
                onClick = {
                    launcher.launch("image/*")
                },
                withoutRipple = false
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Placeholder Avatar",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Select Avatar", color = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {

}