package com.example.managebudget.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.managebudget.R

var Fonts = FontFamily(
    Font(R.font.kalameh_medium, FontWeight.W300),
    Font(R.font.kalameh_bold, FontWeight.W500),
)

val MyTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.W300,
        fontSize = 15.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.W300,
        fontSize = 20.sp
    ),

    labelMedium = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),

)