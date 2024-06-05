package com.pr7.jc_prnote.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pr7.jc_prnote.ui.screens.add.AddScreen
import com.pr7.jc_prnote.ui.screens.home.HomeScreen
import com.pr7.jc_prnote.ui.screens.onboarding.OnBoardingMainScreen
import com.pr7.jc_prnote.ui.screens.splash.SplashScreen


@Composable
fun SetupNavGraph(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Home.route,

    ) {
        composable(Screens.Splash.route){ SplashScreen(navHostController=navHostController)}
        composable(Screens.Home.route){ HomeScreen(navHostController=navHostController) }
        composable(Screens.OnBoard.route){ OnBoardingMainScreen(navHostController=navHostController) }
        composable(Screens.Add.route){ AddScreen(navHostController=navHostController) }
    }
}