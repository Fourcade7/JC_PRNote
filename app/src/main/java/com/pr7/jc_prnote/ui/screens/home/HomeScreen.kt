package com.pr7.jc_prnote.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.uiutils.BasicTextFieldSearchCustom
import com.pr7.jc_prnote.uiutils.ExtendedFAB
import com.pr7.jc_prnote.uiutils.LargeText
import com.pr7.jc_prnote.uiutils.LargeTextSemiBold
import com.pr7.jc_prnote.uiutils.SmallText
import com.pr7.jc_prnote.uiutils.SpacerStd

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var showSearchBar by remember { mutableStateOf(false) }
    val animFloat by animateFloatAsState(
        targetValue = if (showSearchBar) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )
    val focusManager = LocalFocusManager.current

    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(15.dp),

    ) {

       Column {
           NavBar(){
               focusManager.clearFocus()
               showSearchBar=!showSearchBar
           }

           AnimatedVisibility(
               visible =showSearchBar ,
               enter = scaleIn()+ expandVertically(),
               exit = scaleOut()+ shrinkVertically()
           ) {SearchBar()}

           Text(text = "Axxaxa")


       }

        
        ExtendedFAB(
            modifier=Modifier.align(Alignment.BottomEnd),
            text = "Добавлять"
        ) {

        }

        
    }
}


@Composable
private fun NavBar(
    onClick:()->Unit
) {

    Row(
        modifier=Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        LargeTextSemiBold(text = "Pr Note")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                modifier = Modifier.size(33.dp),
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search"
            )
        }
        IconButton(onClick = {  }) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setttings"
            )
        }
    }
    
}


@Composable
private fun SearchBar(modifier: Modifier=Modifier) {
    Column(
        modifier=modifier
    ) {
        
        SmallText(text = "Поиск")
        SpacerStd(height = 10)
        BasicTextFieldSearchCustom()
        
    }
}