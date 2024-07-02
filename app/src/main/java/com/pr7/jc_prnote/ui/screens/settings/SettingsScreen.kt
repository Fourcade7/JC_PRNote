@file:OptIn(ExperimentalLayoutApi::class)

package com.pr7.jc_prnote.ui.screens.settings

import SharedPrefManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.local.datastore.DataStoreManager
import com.pr7.jc_prnote.data.local.datastore.LANGGG
import com.pr7.jc_prnote.data.local.datastore.THEME
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarker
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarkerChild
import com.pr7.jc_prnote.uiutils.LargeTextSemiBold
import com.pr7.jc_prnote.uiutils.SmallText
import com.pr7.jc_prnote.uiutils.SpacerStd
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    dataStoreManager: DataStoreManager
) {

    val scope = rememberCoroutineScope()
    val theme by dataStoreManager.loadBoolean(THEME).collectAsState(initial = true)
    //val langg by dataStoreManager.loadString(LANGGG).collectAsState(initial = null)
    val langg =SharedPrefManager.loadString(LANGGG)
    Log.d(TAG, "datastore: $langg")
    var openLanguageList by remember {
       mutableStateOf(false)
    }
    
    val languageList by remember {
        mutableStateOf(listOf(
            "Eng",
            "Ру",
            "Uz",

        ))
    }


    val lang= langg?:(AppCompatDelegate.getApplicationLocales().get(0)?.language?: Locale.current.language)

    val sellang=when(lang){
        "en"->"Eng"
        "ru"->"Ру"
        "uz"->"Uz"
        else->lang
    }

    var selectedLanguage by remember {
        mutableStateOf(sellang)
    }
    Log.d(TAG, "default language: $sellang")


    Column(modifier= Modifier
        .fillMaxSize()
        .padding(15.dp)) {
        LargeTextSemiBold(text = stringResource(R.string.settings))
        SpacerStd(height = 10)

        Card(
            onClick = {
                scope.launch {
                    dataStoreManager.saveBoolean(THEME,!theme)
                }
            }
        ) {
           Row(
               modifier= Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 10.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               SmallText(text = stringResource(id = R.string.themes), color = MaterialTheme.colorScheme.onSecondary)
               Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                scope.launch {
                    dataStoreManager.saveBoolean(THEME,!theme)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.brightness),
                    contentDescription = "br",
                    modifier=Modifier.size(24.dp)
                )
            }
           }
        }

        SpacerStd(height = 10)

        Card(
            onClick = {
                openLanguageList=!openLanguageList
            }
        ) {
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallText(text = stringResource(id = R.string.changelang),color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {  openLanguageList=!openLanguageList }) {
                    Icon(
                        painter = painterResource(id = R.drawable.translate),
                        contentDescription = "br",
                        modifier=Modifier.size(24.dp)
                    )
                }
            }
        }
        
        
        //Radio buttons here
        SpacerStd(height = 10)
        AnimatedVisibility(visible = openLanguageList) {
            FlowRow(verticalArrangement = Arrangement.Center){
               Row(verticalAlignment = Alignment.CenterVertically) {
                   languageList.forEach {

                       RadioButton(
                           selected = selectedLanguage==it,
                           onClick = {
                               selectedLanguage=it

                               when(it){
                                   "Eng"->{AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en")) }
                                   "Ру"->{AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru")) }
                                   "Uz"->{AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("uz")) }
                               }

                               SharedPrefManager.saveString(LANGGG,it)
                           }
                       )

                       Text(text = it)

                   }
               }
            }
        }

        
        
    }

}