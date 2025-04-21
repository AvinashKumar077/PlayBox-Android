package com.martin.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@Composable
fun AuthScreen(){
    AuthScreenContent()
}

@Composable
fun AuthScreenContent() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val offsetY = with(LocalDensity.current) { (screenWidthDp * 9f / 16f).toPx() * 0.8f }

    var avatarUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var coverUri by rememberSaveable { mutableStateOf<Uri?>(null) }

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

        // Main content overlaid
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = with(LocalDensity.current) { offsetY.toDp() + 80.dp }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Just below avatar

            AuthTextField(value = fullName, onValueChange = { fullName = it }, label = "Full Name")
            AuthTextField(value = email, onValueChange = { email = it }, label = "Email")
            AuthTextField(value = username, onValueChange = { username = it }, label = "Username")
            AuthTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Trigger sign up */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Sign Up")
            }
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
            focusedBorderColor = Color(0xfff29a35),
            unfocusedBorderColor = Color(0xfff29a35),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedLabelColor = Color(0xfff29a35),
            unfocusedLabelColor = Color(0xfff29a35),
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
            .clickable { launcher.launch("image/*") }
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Cover Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
            .clickable { launcher.launch("image/*") }
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
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Placeholder Avatar",
                tint = Color.DarkGray,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}








@Preview(showBackground = true)
@Composable
fun AuthScreenPreview(){
    AuthScreenContent()
}