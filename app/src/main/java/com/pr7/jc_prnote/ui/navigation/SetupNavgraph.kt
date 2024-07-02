package com.pr7.jc_prnote.ui.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pr7.jc_prnote.data.local.datastore.DataStoreManager
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.ui.screens.add.AddScreen
import com.pr7.jc_prnote.ui.screens.add.AddViewModel
import com.pr7.jc_prnote.ui.screens.home.HomeScreen
import com.pr7.jc_prnote.ui.screens.home.HomeViewModel
import com.pr7.jc_prnote.ui.screens.onboarding.OnBoardingMainScreen
import com.pr7.jc_prnote.ui.screens.settings.SettingsScreen
import com.pr7.jc_prnote.ui.screens.splash.SplashScreen
import com.pr7.jc_prnote.ui.screens.update.UpdateScreen
import com.pr7.jc_prnote.ui.screens.update.UpdateViewModel


@Composable
fun SetupNavGraph(
    navHostController: NavHostController = rememberNavController(),
    addViewModel: AddViewModel,
    homeViewModel: HomeViewModel,
    updateViewModel: UpdateViewModel,
    dataStoreManager: DataStoreManager
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Splash.route,

        ) {
        composable(Screens.Splash.route) { SplashScreen(navHostController = navHostController) }
        composable<Screens.Home> {
            HomeScreen(
                navHostController = navHostController,
                homeViewModel = homeViewModel
            )
        }
        composable(Screens.OnBoard.route) { OnBoardingMainScreen(navHostController = navHostController) }
        composable(Screens.Add.route) {
            AddScreen(
                navHostController = navHostController,
                addViewModel = addViewModel
            )
        }

        composable<Screens.Update> {
            val data=it.toRoute<Screens.Update>()
            Log.d(TAG, "SetupNavGraph: ${data.uid}")
            val note=Note(
                uid = data.uid,
                title = data.title,
                description = data.description,
                dataTime = data.dataTime,
                dataTime2 = data.dataTime2,
                priority = data.priority,
                category = data.category,
                key = data.key,
                backgroundColor = data.backgroundColor
                )
            UpdateScreen(
                navHostController = navHostController,
                updateViewModel = updateViewModel,
                note = note
            )
        }



        composable(Screens.Settings.route) { SettingsScreen(dataStoreManager = dataStoreManager) }

    }
}