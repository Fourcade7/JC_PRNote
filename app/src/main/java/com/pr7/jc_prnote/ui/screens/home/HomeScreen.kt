@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.model.Category
import com.pr7.jc_prnote.ui.navigation.Screens
import com.pr7.jc_prnote.uiutils.BasicTextFieldSearchCustom
import com.pr7.jc_prnote.uiutils.ExtendedFAB
import com.pr7.jc_prnote.uiutils.LargeTextSemiBold
import com.pr7.jc_prnote.uiutils.MediumText
import com.pr7.jc_prnote.uiutils.SmallText
import com.pr7.jc_prnote.uiutils.SpacerStd

@Composable
fun HomeScreen(navHostController: NavHostController) {

    var showSearchBar by remember { mutableStateOf(false) }
    val animFloat by animateFloatAsState(
        targetValue = if (showSearchBar) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )
    val focusManager = LocalFocusManager.current
    // focusManager.moveFocus(FocusDirection.Next) //go to next textfield

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
               visible =showSearchBar,
               enter = scaleIn(animationSpec = tween(200)) + expandVertically(animationSpec = tween(400)),
               //exit = scaleOut()+ shrinkVertically()
               exit = scaleOut(animationSpec = tween(200)) + shrinkVertically(animationSpec = tween(400))
           ) {SearchBar()}
           
           SpacerStd(height = 8)
           CategoryScreen()
           SpacerStd(height = 10)
           ReadAllNotes()



       }

        
        ExtendedFAB(
            modifier=Modifier.align(Alignment.BottomEnd),
            text = "Добавлять"
        ) {
            navHostController.navigate(Screens.Add.route)
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
        Icon(
            painter = painterResource(id = R.drawable.sn),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
            //tint = if (selectedItem==it) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary
        )
        SpacerStd(width = 7)
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
    var searchTitle by remember {
        mutableStateOf("")
    }
    Column(
        modifier=modifier
    ) {
        

        SpacerStd(height = 10)
        BasicTextFieldSearchCustom(name = searchTitle){
            searchTitle=it
        }
        
    }
}

@Composable
private fun CategoryScreen() {

    val categorys by remember {
        mutableStateOf(
            listOf(
                Category(name = "Работа", image = R.drawable.work),
                Category(name = "Дом", image = R.drawable.home),
                Category(name = "Еда", image = R.drawable.food),
                Category(name = "Образование", image = R.drawable.education),
                Category(name = "Семья", image = R.drawable.family),
                Category(name = "Деньги", image = R.drawable.wallet),
                Category(name = "Долг", image = R.drawable.liability),
                Category(name = "Здоровье", image = R.drawable.health),
                Category(name = "Спорт", image = R.drawable.sports),
                Category(name = "GYM", image = R.drawable.gym),
                Category(name = "Книга", image = R.drawable.book),
                Category(name = "Одежда", image = R.drawable.clothes),
                Category(name = "Смотреть", image = R.drawable.watching),
                Category(name = "Отдых", image = R.drawable.sunbed),
                Category(name = "Машина", image = R.drawable.car),
                Category(name = "Я пойду", image = R.drawable.direction),
                Category(name = "Каникулы", image = R.drawable.holidays),
            )
        )

    }
    var selectedItem by remember {
        mutableStateOf(Category(name = "Работа", image = R.drawable.work))
    }


    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        items(categorys) {
            Card(

                border = BorderStroke(width = 1.dp, color = if (selectedItem==it) Color.Transparent else Color.Transparent),
                colors = CardDefaults.cardColors(containerColor = if (selectedItem==it) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface),
                onClick = {
                    selectedItem=it
//                    categorys=categorys.map {
//                        if (it.isSelected){
//                            it.copy(isSelected = false)
//                        }else{
//                            it.copy(isSelected = true)
//                        }
//
//                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = it.image),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = if (selectedItem==it) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSecondary
                    )
                    SpacerStd(width = 5)
                    SmallText(text = it.name,color=if (selectedItem==it) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}


@Composable
private fun ReadAllNotes() {

    val array = remember {
        listOf("A","B","C")
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalItemSpacing = 7.dp
    ) {
        items(array){
            LazyItemScreen(text = it)
        }
    }

}

@Composable
private fun LazyItemScreen(text:String) {
    var checked by remember {
        mutableStateOf(false)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.rocket),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                SpacerStd(width = 8)
                Icon(
                    painter = painterResource(id = R.drawable.car),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                SpacerStd(width = 10)
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { },
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            SpacerStd(height = 3)
            MediumText(text = "Title asdfsdfaasdfasdf")
            SmallText(text = "Description")
            SpacerStd(height = 4)
            Divider()
            SpacerStd(height = 4)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallText(text = "item 1")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked=!checked},
                    modifier = Modifier.size(20.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.onPrimary,
                        checkmarkColor = MaterialTheme.colorScheme.onSurface,
                        uncheckedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
            SpacerStd(height = 4)
            Divider()
            SpacerStd(height = 4)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallText(text = "item 1")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked=!checked},
                    modifier = Modifier.size(20.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.onPrimary,
                        checkmarkColor = MaterialTheme.colorScheme.onSurface,
                        uncheckedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
            SpacerStd(height = 8)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                //Spacer(modifier = Modifier.weight(1f))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        SmallText(text = "16:04", color =MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

        }

    }


}