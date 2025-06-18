package com.martin.features.home.bottomsheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentInputBottomSheet(
    sheetState: SheetState,
    onSend: (String) -> Unit,
    onDismiss: () -> Unit,
    currentUserAvatar: String?,
) {
    val textFieldState = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RectangleShape,
        dragHandle = {},
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.DarkGray,
                ),
            color = Color(0xff111111),
            tonalElevation = 4.dp // Optional shadow
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LaunchedEffect(Unit) {
                    delay(200)
                    focusRequester.requestFocus()
                }
                Image(
                    painter = rememberAsyncImagePainter(currentUserAvatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 10.dp)
                        .size(25.dp)
                        .clip(CircleShape)
                )
                OutlinedTextField(
                    state = textFieldState,
                    lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 2),
                    label = {  },
                    textStyle = MaterialTheme.typography.bodySmall,
                    shape = RoundedCornerShape(2.dp),
                    placeholder = { Text("Add a comment...") },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,

                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,

                        focusedContainerColor = Color(0xff111111),
                        unfocusedContainerColor = Color(0xff111111)
                    ),
                )
                IconButton(onClick = {
                    onSend(textFieldState.text.toString())
                    textFieldState.clearText() // clear after sending
                }, enabled = textFieldState.text.isNotBlank()) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
