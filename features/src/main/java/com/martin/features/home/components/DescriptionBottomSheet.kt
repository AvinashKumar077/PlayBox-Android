package com.martin.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.martin.core.helper.DateUtils.getFormattedDateForDisplay
import com.martin.core.ui.sans
import com.martin.core.utils.extensions.noRippleClickable
import com.martin.features.home.videoutils.CustomSubscribeButton
import com.martin.features.home.videoutils.RollingCounter

@Composable
fun DescriptionBottomSheetContent(
    likes: Int?,
    views: Int?,
    createdDateTime: String?,
    description: String?,
    channelName: String?,
    avatar: String?,
    title: String?,
    isSubscribed: Boolean,
    subscriberCount:Int,
    isLoadings: Boolean = false,
    onSubscribeClicked: () -> Unit,
    onDismiss: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Description",
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = sans,
                fontWeight = FontWeight.SemiBold,
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp).noRippleClickable(
                    onClick = {
                        onDismiss()
                    }
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.DarkGray
        )

        Text(
            text = title ?: "",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = sans,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RollingCounter(targetNumber = likes)
                Text(
                    text = "Likes",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontFamily = sans
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RollingCounter(targetNumber = views)
                Text(
                    text = "Views",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontFamily = sans
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val (dayMonth, year) = getFormattedDateForDisplay(dateString = createdDateTime.toString())
                Text(
                    text = dayMonth, color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = sans
                )
                Text(
                    text = year, color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontFamily = sans
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color(0xff111111), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = description.toString(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontFamily = sans,
                modifier = Modifier.padding(16.dp)
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = channelName.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = sans,
                )
                Text(
                    text = "$subscriberCount subscribers",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = sans,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomSubscribeButton(
                onClickAction = {onSubscribeClicked()},
                isSubscribed = isSubscribed,
                isLoadings = isLoadings
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewDescriptionBottomSheet() {
    DescriptionBottomSheetContent(
        onDismiss = {},
        onSubscribeClicked = {},
        likes = 123,
        views = 123,
        createdDateTime = "2025-03-28T10:00:00.000Z",
        description = "this is the description of the video",
        channelName = "Martin",
        avatar = "https://th.bing.com/th/id/OIP.eTUT6_ZG9aGQ8cACsc7nLQHaFj?w=260&h=195&c=7&r=0&o=5&dpr=1.3&pid=1.7",
        title = "this is the title of the video",
        isSubscribed = true,
        subscriberCount = 12
    )
}