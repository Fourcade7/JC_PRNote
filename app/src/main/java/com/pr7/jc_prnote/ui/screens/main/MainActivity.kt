@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.pr7.jc_prnote.data.local.datastore.DataStoreManager
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.RoomInstance
import com.pr7.jc_prnote.ui.navigation.SetupNavGraph
import com.pr7.jc_prnote.ui.screens.main.theme.JC_PRNoteTheme
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager= DataStoreManager(this)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.Black.toArgb(),Color.White.toArgb()))


        setContent {
            JC_PRNoteTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                   Column(modifier = Modifier.fillMaxHeight().padding(innerPadding).imePadding()) {

                       MainScreen(dataStoreManager)


                   }
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(dataStoreManager: DataStoreManager) {

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



    SetupNavGraph()

}
