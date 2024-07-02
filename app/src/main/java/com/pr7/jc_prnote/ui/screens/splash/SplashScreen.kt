package com.pr7.jc_prnote.ui.screens.splash

import ONBOARDING
import SharedPrefManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.ui.navigation.Screens
import com.pr7.jc_prnote.ui.screens.main.theme.JC_PRNoteTheme
import com.pr7.jc_prnote.uiutils.SpacerStd
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navHostController: NavHostController) {

    var startAnimation by remember { mutableStateOf(false) }
    val animFloat by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )
    val onboarding = SharedPrefManager.loadBoolean(ONBOARDING)

    LaunchedEffect(key1 = true) {
        startAnimation=true
        delay(4000)
        navHostController.popBackStack()

        if (onboarding){
            navHostController.navigate(Screens.Home)
        }else{
            navHostController.navigate(Screens.OnBoard.route)
        }

    }
    
   JC_PRNoteTheme(darkTheme = false) {
       Column(
           modifier=Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Icon(
               painter = painterResource(id =R.drawable.notebook),
               contentDescription = "D",
               tint = Color.White,
               modifier = Modifier
                   .size(120.dp)
                   .alpha(alpha = animFloat)
           )
           SpacerStd(height = 10)
           Text(
               text = stringResource(id = R.string.app_name),
               fontSize = 20.sp,


           )
           Text(
               text = stringResource(id = R.string.discipline),
               modifier = Modifier
                   .background(Color.Red)
                   .padding(horizontal = 10.dp)
           )
       }
   }
}