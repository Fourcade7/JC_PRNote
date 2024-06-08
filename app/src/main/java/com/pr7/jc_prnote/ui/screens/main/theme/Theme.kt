package com.pr7.jc_prnote.ui.screens.main.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,

    //tertiary = Color(0xFF98C0BB),

    //Background
    onPrimary = Color(0xFF009688),
    onSurface = Color(0xFF363636),

    //TEXT
    onTertiary = Color(0xFF98C0BB), //Selected
    //MAIN COLOR
    onSecondary = Color(0xFFC2C2C2),

  /*
    //TEXT
    tertiary = Color(0xFF98C0BB),

    //Background
    onPrimary = Color(0xFF009688),
    onSurface = Color(0xFF1D3A36),

    //TEXT
    onTertiary = Color(0xFF98C0BB), //Selected
    onSecondary = Color(0xFF5DA89E),
    */



)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,

    tertiary = Color.Black,

    //Background
    onPrimary = Color(0xFF009688),
    onSurface = Color(0xFFE9E9E9),

    //TEXT
    //MAIN COLOR
    onSecondary = Color(0xFF1C2726),
    onTertiary = Color.White,
    //TEXT
     /*

     tertiary = Color.Black,

    //Background
    onPrimary = Color(0xFF009688),
    onSurface = Color(0xFFE7F1F0),

    //TEXT
    onSecondary = Color(0xFF047A6F),
    onTertiary = Color.White,


    */


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun JC_PRNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}