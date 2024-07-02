@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat
import com.pr7.jc_prnote.data.local.datastore.DataStoreManager
import com.pr7.jc_prnote.data.local.datastore.LANGGG
import com.pr7.jc_prnote.data.local.datastore.THEME
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.ui.navigation.SetupNavGraph
import com.pr7.jc_prnote.ui.screens.add.AddViewModel
import com.pr7.jc_prnote.ui.screens.home.HomeViewModel
import com.pr7.jc_prnote.ui.screens.main.theme.JC_PRNoteTheme
import com.pr7.jc_prnote.ui.screens.update.UpdateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi


class MainActivity : AppCompatActivity() {

    val addViewModel:AddViewModel by viewModels<AddViewModel>()
    val homeViewModel:HomeViewModel by viewModels<HomeViewModel>()
    val updateViewModel:UpdateViewModel by viewModels<UpdateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStoreManager= DataStoreManager(this)


        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.Red.toArgb(),Color.White.toArgb()))





        setContent {
            Log.d(TAG, "isSystemDarktheme: ${isSystemInDarkTheme()}")
            //AppCompatDelegate.MODE_NIGHT_YES
            val theme by dataStoreManager.loadBoolean(THEME).collectAsState(initial = true)

            JC_PRNoteTheme() {
                //if ((AppCompatDelegate.getApplicationLocales().get(0)?.language?: Locale.current.language) =="ru")
                //AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                val langg = SharedPrefManager.loadString(LANGGG)
                val lang=langg?:AppCompatDelegate.getApplicationLocales().get(0)?.language?: Locale.current.language
                val sellang=when(lang){
                    "Eng"->"en"
                    "Ру"->"ru"
                    "Uz"->"uz"
                    else->lang
                }
                Log.d(TAG, "onCreate PR777: $sellang")

                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(sellang))



                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                   Column(modifier = Modifier
                       .fillMaxHeight()
                       .padding(innerPadding)
                       .imePadding()) {

                       MainScreen(
                           dataStoreManager,
                           addViewModel = addViewModel,
                           homeViewModel = homeViewModel,
                           updateViewModel=updateViewModel
                       )


                   }
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    dataStoreManager: DataStoreManager,
    addViewModel: AddViewModel,
    homeViewModel: HomeViewModel,
    updateViewModel: UpdateViewModel
) {

//    val getdata by dataStoreManager.loadString("key100").collectAsState(initial = "")
//    val scope = rememberCoroutineScope()
//    scope.launch {
//
//        Log.d("PR77777", "MainScreen: ${getdata}")
//        dataStoreManager.saveString("key1000","Pr777 whatta")
//    }
//    if (getdata!=null){
//        Text(text = getdata.toString())
//    }



    SetupNavGraph(
        addViewModel = addViewModel,
        homeViewModel=homeViewModel,
        updateViewModel = updateViewModel,
        dataStoreManager = dataStoreManager
    )

}
