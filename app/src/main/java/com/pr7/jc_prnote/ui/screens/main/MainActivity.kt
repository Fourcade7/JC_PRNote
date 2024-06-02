@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pr7.jc_prnote.data.local.DataStoreManager
import com.pr7.jc_prnote.ui.navigation.SetupNavGraph
import com.pr7.jc_prnote.ui.screens.main.theme.JC_PRNoteTheme
import com.pr7.jc_prnote.uiutils.BasicTextFieldCustom
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager=DataStoreManager(this)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.Black.toArgb(),Color.White.toArgb()))
        setContent {
            JC_PRNoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Column(modifier = Modifier.padding(innerPadding).imePadding()) {

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
