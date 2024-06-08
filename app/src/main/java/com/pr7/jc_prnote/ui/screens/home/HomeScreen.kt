@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.data.model.Category
import com.pr7.jc_prnote.data.model.Priority
import com.pr7.jc_prnote.ui.navigation.Screens
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarker
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarkerChild
import com.pr7.jc_prnote.uiutils.BasicTextFieldSearchCustom
import com.pr7.jc_prnote.uiutils.CircularCheckbox
import com.pr7.jc_prnote.uiutils.CustomIcon
import com.pr7.jc_prnote.uiutils.DescriptionText
import com.pr7.jc_prnote.uiutils.ExtendedFAB
import com.pr7.jc_prnote.uiutils.LargeTextSemiBold
import com.pr7.jc_prnote.uiutils.MediumText
import com.pr7.jc_prnote.uiutils.SmallText
import com.pr7.jc_prnote.uiutils.SpacerStd

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel
) {

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
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),

        ) {

        Column {
            NavBar() {
                focusManager.clearFocus()
                showSearchBar = !showSearchBar
            }

            AnimatedVisibility(
                visible = showSearchBar,
                enter = scaleIn(animationSpec = tween(200)) + expandVertically(
                    animationSpec = tween(
                        400
                    )
                ),
                //exit = scaleOut()+ shrinkVertically()
                exit = scaleOut(animationSpec = tween(200)) + shrinkVertically(
                    animationSpec = tween(
                        400
                    )
                )
            ) { SearchBar() }

            SpacerStd(height = 8)
            CategoryScreen()
            SpacerStd(height = 10)
            ReadAllNotes(homeViewModel = homeViewModel)


        }




        ExtendedFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = "Добавлять"
        ) {
            navHostController.navigate(Screens.Add.route)
        }


    }
}


@Composable
private fun NavBar(
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
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
        IconButton(onClick = { }) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setttings"
            )
        }
    }

}


@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
    var searchTitle by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
    ) {


        SpacerStd(height = 10)
        BasicTextFieldSearchCustom(name = searchTitle) {
            searchTitle = it
        }

    }
}

@Composable
private fun CategoryScreen() {

    val categorys by remember {
        mutableStateOf(
            listOf(
                Category(name = "Все", image = R.drawable.all),
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
        mutableStateOf(Category(name = "Все", image = R.drawable.all))
    }


    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        items(categorys) {
            Card(

                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedItem == it) Color.Transparent else Color.Transparent
                ),
                colors = CardDefaults.cardColors(containerColor = if (selectedItem == it) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface),
                onClick = {
                    selectedItem = it
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
                    if (selectedItem == it){
                    Icon(
                        painter = painterResource(id = it.image),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                    )
                    SpacerStd(width = 5)

                        SmallText(
                            text = it.name,
                            color = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                        )
                    }else{
                        Icon(
                            painter = painterResource(id = it.image),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            //tint = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                        )
                        SpacerStd(width = 5)
                        SmallText(
                            text = it.name,
                        )
                    }

                }
            }
        }
    }
}


@Composable
private fun ReadAllNotes(
    homeViewModel: HomeViewModel
) {


    Log.d(TAG, "ReadAllNotes: ${homeViewModel.allNotesList}")
    Log.d(TAG, "ReadAllNotes: ${homeViewModel.allNotesList.isEmpty()}")

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalItemSpacing = 7.dp
    ) {
        items(homeViewModel.allNotesList.reversed()) {
            LazyItemScreen(
                key = it.key.toString(),
                homeViewModel = homeViewModel,
                note = it
            )
        }
    }

}

@Composable
private fun LazyItemScreen(
    key: String,
    homeViewModel: HomeViewModel,
    note: Note
) {


    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (note.backgroundColor != null) Color(
                note.backgroundColor.toColorInt()
            ) else MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (note.priority!=null){
                    if (note.backgroundColor != null){
                        CustomIcon(
                            image = ReturnPrImageToInt(note.priority.toString())!!,
                            color = BackgroundDarker
                        )
                    }else{
                        CustomIcon(
                            image = ReturnPrImageToInt(note.priority.toString())!!,
                        )
                    }

                }
                //image = ReturnImageToInt(note.category.toString())!!,
                SpacerStd(width = 8)
                if(note.category!=null){
                    if (note.backgroundColor != null){
                        CustomIcon(image = ReturnImageToInt(note.category.toString())!!, color = BackgroundDarker)
                    }else{
                        CustomIcon(image = ReturnImageToInt(note.category.toString())!!)
                    }
                }
                SpacerStd(width = 10)
                if (note.backgroundColor != null){
                    CustomIcon(image = R.drawable.more, color = BackgroundDarker)
                }else{
                    CustomIcon(image = R.drawable.more)
                }

            }
            SpacerStd(height = 3)
            MediumText(text = note.title.toString(), color = if (note.backgroundColor != null) Color.Black else MaterialTheme.colorScheme.onSecondary)
            DescriptionText(text = note.description.toString(),color = if (note.backgroundColor != null) Color.Black else MaterialTheme.colorScheme.onSecondary)
            SpacerStd(height = 4)


            MultiTaskItemScreen(bgcolor = note.backgroundColor, key = key, homeViewModel = homeViewModel)
            //SpacerStd(height = 4)

            SpacerStd(height = 8)
            //Date TIME 1
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BackgroundDarker),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                       SmallText(
                            text = note.dataTime.toString(),
                            color = BackgroundDarkerChild
                        )
                    }
                }
            }

            //Date TIME 2
            if (note.dataTime2 != null) {
                SpacerStd(height = 4)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BackgroundDarker),
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            SmallText(
                                text = note.dataTime2.toString(),
                                color = BackgroundDarkerChild
                            )
                        }
                    }
                }
            }
            //Date TIME 2


        }

    }

}


@Composable
private fun MultiTaskItemScreen(
    bgcolor:String?,
    key: String,
    homeViewModel: HomeViewModel
) {
    var checked by remember {
        mutableStateOf(false)
    }
    Column {


        homeViewModel.allMultiTaskList
            .filter {
                it.key.toString() == key
            }
            .forEach {
                Divider(
                    color = Color.DarkGray,
                    thickness = .5.dp
                )
                SpacerStd(height = 4)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (bgcolor!=null){
                        SmallText(text = it.title.toString(), color = BackgroundDarker)
                    }else{
                        SmallText(text = it.title.toString())
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    CircularCheckbox(bgcolor=bgcolor,checked = checked) {
                        checked = !checked
                    }
                }
                SpacerStd(height = 4)
                //Divider()
            }



    }

}


private fun ReturnImageToInt(
    imgName: String
):Int? {


    val clist =listOf(
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
    val img= clist.filter {
        imgName==it.name
    }
    try {
        Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image
    }catch (e:Exception){
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return null
    }
}

private fun ReturnPrImageToInt(
    imgName: String
):Int? {
    val array = listOf(
        Priority(name = "Важное", image = R.drawable.rocket),
        Priority(name = "Высокий", image = R.drawable.bomb),
        Priority(name = "Средний", image = R.drawable.gauge),
        Priority(name = "Низкий", image = R.drawable.convenient),
    )
    val img= array.filter {
        imgName==it.name
    }
    try {
        //Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image
    }catch (e:Exception){
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return null
    }
}

