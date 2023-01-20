package com.example.movies_catalog.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movies_catalog.R

val IbmPlexSans = FontFamily(
    Font(R.font.ibm_plex_sans_regular),
    Font(R.font.ibm_plex_sans_bold, FontWeight.Bold),
    Font(R.font.ibm_plex_sans_medium, FontWeight.Medium)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h3 = TextStyle(
        fontSize = 20.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),
    h4 = TextStyle(
        fontSize = 36.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 12.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        lineHeight = 15.sp
    ),
    subtitle2 = TextStyle(
        fontSize = 12.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp
    ),
    caption = TextStyle(
        fontSize = 13.7.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        lineHeight = 17.sp
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)