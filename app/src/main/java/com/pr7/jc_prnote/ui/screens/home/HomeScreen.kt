@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.local.room.MultiTask
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
import com.pr7.jc_prnote.uiutils.DropdownMenuNoPaddingVeitical
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

    // Log.d(TAG, "getByIdNote: ${homeViewModel.getByIdNote?.title}")

    var showSearchBar by remember { mutableStateOf(false) }
    val animFloat by animateFloatAsState(
        targetValue = if (showSearchBar) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )
    val focusManager = LocalFocusManager.current
    // focusManager.moveFocus(FocusDirection.Next) //go to next textfield

    var searchTitle by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),

        ) {

        Column {
            NavBar(navHostController=navHostController) {
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
            ) { SearchBar {searchTitle=it} }

            SpacerStd(height = 8)
            CategoryScreen{searchTitle=it}
            SpacerStd(height = 10)
            ReadAllNotes(navHostController = navHostController, homeViewModel = homeViewModel, searchTitle = searchTitle)


        }




        ExtendedFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.addnewtask),
            icon = Icons.Filled.Add
        ) {
            navHostController.navigate(Screens.Add.route)
        }


    }
}


@Composable
private fun NavBar(
    navHostController: NavHostController,
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
        IconButton(onClick = {
            navHostController.navigate(Screens.Settings.route)
        }) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setttings"
            )
        }
    }

}


@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    sendText:(String)->Unit
) {
    var searchTitle by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
    ) {


        SpacerStd(height = 10)
        BasicTextFieldSearchCustom(name = searchTitle) {
            searchTitle = it
            sendText(searchTitle)
        }

    }
}

@Composable
private fun CategoryScreen(
    sendText:(String)->Unit
) {

    val categorys= listOf(
        Category(name = stringResource(id = R.string.all), image = R.drawable.all),
        Category(name = stringResource(id = R.string.work), image = R.drawable.work),
        Category(name = stringResource(id = R.string.home), image = R.drawable.home),
        Category(name = stringResource(id = R.string.eat), image = R.drawable.food),
        Category(name = stringResource(id = R.string.education), image = R.drawable.education),
        Category(name = stringResource(id = R.string.family), image = R.drawable.family),
        Category(name = stringResource(id = R.string.money), image = R.drawable.wallet),
        Category(name = stringResource(id = R.string.debt), image = R.drawable.liability),
        Category(name = stringResource(id = R.string.health), image = R.drawable.health),
        Category(name = stringResource(id = R.string.sport), image = R.drawable.sports),
        Category(name = stringResource(id = R.string.gym), image = R.drawable.gym),
        Category(name = stringResource(id = R.string.book), image = R.drawable.book),
        Category(name = stringResource(id = R.string.cloth), image = R.drawable.clothes),
        Category(name = stringResource(id = R.string.watch), image = R.drawable.watching),
        Category(name = stringResource(id = R.string.rest), image = R.drawable.sunbed),
        Category(name = stringResource(id = R.string.car), image = R.drawable.car),
        Category(name = stringResource(id = R.string.iwillgo), image = R.drawable.direction),
        Category(name = stringResource(id = R.string.holidays), image = R.drawable.holidays)
    )
    var selectedItem by remember {
        mutableStateOf(Category(name = categorys.get(0).name, image = R.drawable.all))
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
                    if (it.name=="Все" || it.name=="Hammasi" || it.name=="All"){
                        sendText.invoke("")
                    }else{
                        sendText.invoke(it.name.toString())
                    }

                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    if (selectedItem == it) {
                        Icon(
                            painter = painterResource(id = it.image!!),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                        )
                        SpacerStd(width = 5)

                        SmallText(
                            text = it.name.toString(),
                            color = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = it.image!!),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            //tint = if (selectedItem == it && isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                        )
                        SpacerStd(width = 5)
                        SmallText(
                            text = it.name.toString(),
                        )
                    }

                }
            }
        }
    }
}


@Composable
private fun ReadAllNotes(
    searchTitle:String,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel
) {


    //Log.d(TAG, "ReadAllNotes: ${searchTitle}")
//    Log.d(TAG, "ReadAllNotes: ${homeViewModel.allNotesList.isEmpty()}")

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalItemSpacing = 7.dp
    ) {
        items(homeViewModel.allNotesList.filter {
            it.title.toString().contains(searchTitle,ignoreCase = true) ||
            it.description.toString().contains(searchTitle,ignoreCase = true) ||
            it.dataTime.toString().contains(searchTitle,ignoreCase = true) ||
            it.category.toString().contains(searchTitle,ignoreCase = true)
        }.reversed()) {
            LazyItemScreen(
                navHostController = navHostController,
                key = it.key.toString(),
                homeViewModel = homeViewModel,
                note = it
            )
        }
    }

}

@Composable
private fun LazyItemScreen(
    navHostController: NavHostController,
    key: String,
    homeViewModel: HomeViewModel,
    note: Note
) {

    //Log.d(TAG, "LazyItemScreen: ${note.backgroundColor}")
    var expandDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (note.backgroundColor != null) Color(
                note.backgroundColor.toColorInt()
            ) else MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            homeViewModel.updateNote(Note(
                uid = note.uid,
                title = note.title,
                description = note.description,
                dataTime = note.dataTime,
                dataTime2 = note.dataTime2,
                priority = note.priority,
                category = note.category,
                key = note.key,
                backgroundColor = note.backgroundColor,
                status = !note.status
            ))
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (note.priority != null) {
                    if (note.backgroundColor != null) {
                        CustomIcon(
                            image = ReturnPrImageToInt(note.priority),
                            color = BackgroundDarker
                        )
                    } else {
                        CustomIcon(
                            image = ReturnPrImageToInt(note.priority),
                        )
                    }

                }
                //image = ReturnImageToInt(note.category.toString())!!,
                SpacerStd(width = 8)
                if (note.category != null) {
                    if (note.backgroundColor != null) {
                        CustomIcon(
                            image = ReturnImageToInt(note.category),
                            color = BackgroundDarker
                        )
                    } else {
                        CustomIcon(image = ReturnImageToInt(note.category))
                    }
                }
                SpacerStd(width = 10)
                if (note.backgroundColor != null) {
                    CustomIcon(image = R.drawable.more, color = BackgroundDarker) {
                        expandDialog = true
                    }
                } else {
                    CustomIcon(image = R.drawable.more) {
                        expandDialog = true
                        //Log.d(TAG, "LazyItemScreen: Custom icon clicked")
                    }
                }


                //DIALOG
                AnimatedVisibility(visible = expandDialog) {

                    DropdownMenuNoPaddingVeitical(
                        modifier = Modifier.padding(end = 10.dp),
                        expanded = expandDialog,
                        onDismissRequest = { expandDialog = false },
                        offset = DpOffset(x = (-30).dp, y = (5).dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(BackgroundDarker)
                                .clickable {
                                    expandDialog = false

                                    navHostController.navigate(
                                        Screens.Update(
                                            uid = note.uid,
                                            title = note.title.toString(),
                                            description = note.description.toString(),
                                            dataTime = note.dataTime.toString(),
                                            dataTime2 = note.dataTime2,
                                            priority = note.priority,
                                            category = note.category,
                                            key = note.key,
                                            backgroundColor = note.backgroundColor
                                        )
                                    )
                                }
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Edit",
                                modifier = Modifier.size(17.dp),
                                tint = BackgroundDarkerChild
                            )
                            SpacerStd(width = 10)
                            SmallText(text = stringResource(id = R.string.edit), color = BackgroundDarkerChild)
                        }
                        SpacerStd(height = 3)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(BackgroundDarker)
                                .clickable {
                                    homeViewModel.deleteNote(note)
                                    expandDialog = false
                                }
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Edit",
                                modifier = Modifier.size(17.dp),
                                tint = Color.Red
                            )
                            SpacerStd(width = 10)
                            SmallText(text = stringResource(id = R.string.delete), color = Color.Red)
                        }


                    }

                }
                //DOALOG


            }
            SpacerStd(height = 3)
            MediumText(
                text = note.title.toString(),
                color = if (note.backgroundColor != null) Color.Black else MaterialTheme.colorScheme.onSecondary,
                textDecoration = if (note.status) TextDecoration.LineThrough else TextDecoration.None
            )
            DescriptionText(
                text = note.description.toString(),
                color = if (note.backgroundColor != null) Color.Black else MaterialTheme.colorScheme.onSecondary,
                textDecoration = if (note.status) TextDecoration.LineThrough else TextDecoration.None
            )
            SpacerStd(height = 4)


            MultiTaskItemScreen(
                bgcolor = note.backgroundColor,
                key = key,
                homeViewModel = homeViewModel
            )
            //SpacerStd(height = 4)

            SpacerStd(height = 8)
            //Date TIME 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(colors = CardDefaults.cardColors(containerColor = BackgroundDarker)) {
                    Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)) {
                        SmallText(
                            text = note.dataTime.toString(), color = BackgroundDarkerChild,
                            textDecoration = if (note.status) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }
            }

            //Date TIME 2
            if (note.dataTime2 != null) {
                SpacerStd(height = 4)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(colors = CardDefaults.cardColors(containerColor = BackgroundDarker)) {
                        Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)) {
                            SmallText(
                                text = note.dataTime2.toString(),
                                color = BackgroundDarkerChild,
                                textDecoration = if (note.status) TextDecoration.LineThrough else TextDecoration.None
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
    bgcolor: String?,
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
                    if (bgcolor != null) {
                        SmallText(text = it.title.toString(), color = BackgroundDarker, textDecoration = if (it.status) TextDecoration.LineThrough else TextDecoration.None,modifier = Modifier.weight(1f))
                    } else {
                        SmallText(text = it.title.toString(),textDecoration = if (it.status) TextDecoration.LineThrough else TextDecoration.None, modifier = Modifier.weight(1f))
                    }

                    //Spacer(modifier = Modifier.weight(1f))
                    SpacerStd(width = 2)
                    CircularCheckbox(bgcolor = bgcolor, checked = it.status) { itt ->
                        //checked = !checked
                        homeViewModel.updateMultitask(
                            MultiTask(
                                uid = it.uid,
                                title = it.title,
                                category = it.category,
                                key = it.key,
                                status = !it.status
                            )
                        )
                    }
                }
                SpacerStd(height = 4)
                //Divider()
            }


    }

}


@SuppressLint("ComposableNaming")
@Composable
private fun ReturnImageToInt(
    imgName: String
): Int {


    val clist=listOf(
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

        // ENG
        Category(name = "All", image = R.drawable.all),
        Category(name = "Work", image = R.drawable.work),
        Category(name = "Home", image = R.drawable.home),
        Category(name = "Еда", image = R.drawable.food),
        Category(name = "Education", image = R.drawable.education),
        Category(name = "Family", image = R.drawable.family),
        Category(name = "Money", image = R.drawable.wallet),
        Category(name = "Debt", image = R.drawable.liability),
        Category(name = "Health", image = R.drawable.health),
        Category(name = "Sport", image = R.drawable.sports),
        Category(name = "GYM", image = R.drawable.gym),
        Category(name = "Book", image = R.drawable.book),
        Category(name = "Cloth", image = R.drawable.clothes),
        Category(name = "Watch", image = R.drawable.watching),
        Category(name = "Rest", image = R.drawable.sunbed),
        Category(name = "Car", image = R.drawable.car),
        Category(name = "I will go", image = R.drawable.direction),
        Category(name = "Holidays", image = R.drawable.holidays),

        // UZ
        Category(name = "Hammasi", image = R.drawable.all),
        Category(name = "Ish", image = R.drawable.work),
        Category(name = "Uy", image = R.drawable.home),
        Category(name = "Ovqat", image = R.drawable.food),
        Category(name = "Ta'lim", image = R.drawable.education),
        Category(name = "Oila", image = R.drawable.family),
        Category(name = "Pul", image = R.drawable.wallet),
        Category(name = "Qarz", image = R.drawable.liability),
        Category(name = "Salomatlik", image = R.drawable.health),
        Category(name = "Sport", image = R.drawable.sports),
        Category(name = "GYM", image = R.drawable.gym),
        Category(name = "Kitob", image = R.drawable.book),
        Category(name = "Kiyim", image = R.drawable.clothes),
        Category(name = "Ko'rish", image = R.drawable.watching),
        Category(name = "Dam olish", image = R.drawable.sunbed),
        Category(name = "Mashina", image = R.drawable.car),
        Category(name = "Men boraman", image = R.drawable.direction),
        Category(name = "Bayramlar", image = R.drawable.holidays),

    )
    val img = clist.filter {

        imgName == it.name

    }
    try {
        Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image!!
    } catch (e: Exception) {
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return 0
    }
}

private fun ReturnPrImageToInt(
    imgName: String
): Int {
    val array = listOf(
        Priority(name = "Важное", image = R.drawable.rocket),
        Priority(name = "Высокий", image = R.drawable.bomb),
        Priority(name = "Средний", image = R.drawable.gauge),
        Priority(name = "От души", image = R.drawable.convenient),
        //ENG
        Priority(name = "Important", image = R.drawable.rocket),
        Priority(name = "High", image = R.drawable.bomb),
        Priority(name = "Medium", image = R.drawable.gauge),
        Priority(name = "From the heart", image = R.drawable.convenient),

        //ENG
        Priority(name = "Muhim", image = R.drawable.rocket),
        Priority(name = "Yuqori", image = R.drawable.bomb),
        Priority(name = "O'rtacha", image = R.drawable.gauge),
        Priority(name = "Yurakdan", image = R.drawable.convenient),
    )
    val img = array.filter {
        imgName == it.name
    }
    try {
        //Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image!!
    } catch (e: Exception) {
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return 0
    }
}

