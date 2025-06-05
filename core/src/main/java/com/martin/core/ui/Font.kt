package com.martin.core.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.martin.core.R

val roboto_regular = Font(resId = R.font.roboto_regular, weight = FontWeight.Normal)
val roboto_medium = Font(resId = R.font.roboto_medium, weight = FontWeight.Medium)
val roboto_semi_bold = Font(resId = R.font.roboto_semi_bold, weight = FontWeight.SemiBold)
val roboto_bold = Font(resId = R.font.roboto_bold, weight = FontWeight.Bold)

val roboto = FontFamily(
    roboto_regular,roboto_medium,roboto_semi_bold,roboto_bold
)