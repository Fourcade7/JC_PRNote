@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.pr7.jc_prnote.ui.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.data.model.Category
import com.pr7.jc_prnote.data.model.Priority
import com.pr7.jc_prnote.ui.navigation.Screens
import com.pr7.jc_prnote.ui.screens.add.AddViewModel
import com.pr7.jc_prnote.ui.screens.add.convertMillisToDate
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarker
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarkerChild
import com.pr7.jc_prnote.ui.screens.select_color.SelectColorScreen
import com.pr7.jc_prnote.uiutils.CustomBasicTextFieldDescription
import com.pr7.jc_prnote.uiutils.CustomBasicTextFieldMulti
import com.pr7.jc_prnote.uiutils.CustomBasicTextFieldWithIcon
import com.pr7.jc_prnote.uiutils.ExtendedFAB
import com.pr7.jc_prnote.uiutils.LargeTextSemiBold
import com.pr7.jc_prnote.uiutils.SmallText
import com.pr7.jc_prnote.uiutils.SpacerStd
import com.pr7.jc_prnote.uiutils.convertMillisToDateWithClock
import java.util.Calendar


@Composable
fun UpdateScreen(
    navHostController: NavHostController,
    updateViewModel: UpdateViewModel,
    note: Note
) {

    var mainTitle by remember {
        mutableStateOf(note.title.toString())
    }
    var descriptionTitle by remember {
        mutableStateOf(note.description.toString())
    }

    var selectedCategoryName by remember {
        mutableStateOf<String?>(note.category)
    }
    var selectedPriorityName by remember {
        mutableStateOf<String?>(note.priority)
    }
    var selectedDate by remember {
        mutableStateOf<String?>(note.dataTime2)
    }
    var selectedColor by remember {
        mutableStateOf<String?>(note.backgroundColor)
    }

    var isErrorEmptyTitle by remember {
        mutableStateOf(false)
    }
    var isErrorEmptyDescription by remember {
        mutableStateOf(false)
    }

    var today by remember {
        mutableStateOf(convertMillisToDateWithClock(System.currentTimeMillis()))
    }

//    var uplist=updateViewModel.allMultiTaskList.map {
//        it.title
//    }
//
//    var mList by remember {
//        mutableStateOf(uplist)
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .verticalScroll(rememberScrollState())
        ) {
            LargeTextSemiBold(text = stringResource(id = R.string.edittask))
            SpacerStd(height = 10)

            CustomBasicTextFieldWithIcon(name = mainTitle, checkError = isErrorEmptyTitle) {
                mainTitle = it
            }
            SpacerStd(height = 10)
            CustomBasicTextFieldDescription(
                name = descriptionTitle,
                checkError = isErrorEmptyDescription
            ) {
                descriptionTitle = it
            }
            //SpacerStd(height = 10)
            AddMultiTask(note = note,updateViewModel = updateViewModel) //{ mList = it }
            Divider()
            AdditionalSettings(
                note=note,
                selectedCategoryTitle = { selectedCategoryName = it },
                selectedPriorityTitle = { selectedPriorityName = it },
                sendSelectedDate = { selectedDate = it },
                sendSelectedColor = { selectedColor = it }
            )

        }

        ExtendedFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.edittask),
            icon = Icons.Filled.Edit
        ) {
            //ADD to Database
            if (mainTitle.isNotEmpty() && descriptionTitle.isNotEmpty()) {
                //val key = System.currentTimeMillis().toString()
                updateViewModel.updateNote(
                    note = Note(
                        uid = note.uid,
                        title = mainTitle,
                        description = descriptionTitle,
                        category = selectedCategoryName,
                        priority = selectedPriorityName,
                        key = note.key,
                        dataTime = today,
                        dataTime2 = selectedDate,
                        backgroundColor = selectedColor,

                        )
                )
                navHostController.popBackStack()

            } else {
                if (mainTitle.isEmpty()) {
                    isErrorEmptyTitle = true
                }
                if (descriptionTitle.isEmpty()) {
                    isErrorEmptyDescription = true
                }


            }


        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
private fun AddMultiTask(
    note: Note,
    updateViewModel: UpdateViewModel,
    //mTList: (List<String>) -> Unit
) {

    val focusManager = LocalFocusManager.current

    var multiTaskTitle by remember {
        mutableStateOf("")
    }

    //Log.d(TAG, "AddMultiTask: ${updateViewModel.allMultiTaskList}")
    var multitasksList by remember {
        mutableStateOf(updateViewModel.allMultiTaskList.filter {
            note.key==it.key
        })
    }
    ////.map { it.title.toString() }

    var mlist by remember {
       mutableStateOf( mutableListOf<MultiTask>())
        
    }
    LaunchedEffect (key1 = Unit){
            //mlist= mlist.apply{addAll(multitasksList)}
           //mlist= mutableListOf<String>().apply { addAll(multitasksList) }
            mlist=multitasksList.toMutableList()
    }

    var switcher by remember {
        mutableStateOf(multitasksList.size>0)
    }
    var openSettings by remember {
        mutableStateOf(multitasksList.size>0)
    }






    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallText(text =  stringResource(id = R.string.addlist), color = MaterialTheme.colorScheme.onSecondary)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                colors = SwitchDefaults.colors(
                    checkedThumbColor = if (isSystemInDarkTheme()) BackgroundDarkerChild else BackgroundDarker,
                    checkedTrackColor = if (isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                ),
                checked = switcher,
                onCheckedChange = {
                    focusManager.clearFocus()
                    switcher = !switcher
                    openSettings = !openSettings

                })
        }
        AnimatedVisibility(
            visible = openSettings,
            enter = scaleIn() + expandVertically(),
            exit = scaleOut() + shrinkVertically()
        ) {
            Column {

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    multitasksList.forEachIndexed { index, s ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {

                                SmallText(
                                    text = "${index + 1}. ${s.title}",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                                SpacerStd(width = 3)
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = {
                                        mlist.remove(s)
                                        multitasksList = mutableListOf<MultiTask>().apply { addAll(mlist) }
                                        //mTList.invoke(multitasksList)
                                        updateViewModel.deleteMultiTask(s)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Close",
                                        tint = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        CustomBasicTextFieldMulti(name = multiTaskTitle) {
                            multiTaskTitle = it
                        }
                    }
                    IconButton(onClick = {
                        val mtask=MultiTask(title = multiTaskTitle, category = note.category, key = note.key)
                        mlist.add(mtask)
                        multitasksList = mutableListOf<MultiTask>().apply { addAll(mlist) }
                        multiTaskTitle = ""

                        //mTList.invoke(multitasksList)
                        updateViewModel.addMultiTask(mtask)

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done",
                            tint = if (isSystemInDarkTheme()) BackgroundDarkerChild else BackgroundDarker
                        )
                    }

                }



                SpacerStd(height = 10)
            }
        }

    }
}

@Composable
private fun AdditionalSettings(
    note: Note,
    selectedCategoryTitle: (String) -> Unit,
    selectedPriorityTitle: (String) -> Unit,
    sendSelectedDate: (String) -> Unit,
    sendSelectedColor: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var switcher by remember {
        mutableStateOf(if (note.category!=null || note.priority!=null || note.backgroundColor!=null || note.dataTime2!=null) true else false)
    }
    var openSettings by remember {
        mutableStateOf(if (note.category!=null || note.priority!=null || note.backgroundColor!=null || note.dataTime2!=null) true else false)
    }





    Column(

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallText(
                text = stringResource(id = R.string.additionalsettings),
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                colors = SwitchDefaults.colors(
                    checkedThumbColor = if (isSystemInDarkTheme()) BackgroundDarkerChild else BackgroundDarker,
                    checkedTrackColor = if (isSystemInDarkTheme()) BackgroundDarker else BackgroundDarkerChild
                ),
                checked = switcher,
                onCheckedChange = {
                    focusManager.clearFocus()
                    switcher = !switcher
                    openSettings = !openSettings
                })
        }
        AnimatedVisibility(
            visible = openSettings,
            enter = scaleIn() + expandVertically(),
            exit = scaleOut() + shrinkVertically()
        ) {
            Column {

                Divider()

                SmallText(text = stringResource(id = R.string.selectcategory), color = MaterialTheme.colorScheme.tertiary)
                SpacerStd(height = 1)
                CategoryScreen(note=note) { selectedCategoryTitle.invoke(it) }
                Divider()
                SpacerStd(height = 1)
                SmallText(text = stringResource(id = R.string.selectdates), color = MaterialTheme.colorScheme.tertiary)
                DateTimeScreen(note=note) { sendSelectedDate.invoke(it) }
                Divider()
                SpacerStd(height = 8)
                SmallText(text = stringResource(id = R.string.selbgcolor), color = MaterialTheme.colorScheme.tertiary)
                SpacerStd(height = 8)
                SelectColorScreen(note=note) { sendSelectedColor.invoke(it) }

                SpacerStd(height = 8)
                Divider()
                SmallText(text = stringResource(id = R.string.priority), color = MaterialTheme.colorScheme.tertiary)
                PriorityScreen(note=note) { selectedPriorityTitle.invoke(it) }
                SpacerStd(height = 8)


            }
        }

    }

}


@Composable
private fun CategoryScreen(
    note: Note,
    selectedCategoryName: (String) -> Unit
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
        mutableStateOf(Category(name = note.category.toString(), image = ReturnImageToInt(note.category.toString())))
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
                    selectedCategoryName.invoke(it.name.toString())

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
private fun PriorityScreen(
    note: Note,
    selectedPriorityName: (String) -> Unit
) {

    val array = listOf(
        Priority(name = stringResource(id = R.string.important), image = R.drawable.rocket),
        Priority(name = stringResource(id = R.string.high), image = R.drawable.bomb),
        Priority(name = stringResource(id = R.string.medium), image = R.drawable.gauge),
        Priority(name = stringResource(id = R.string.fromheart), image = R.drawable.convenient),
    )


    var selectedItem by remember {
        mutableStateOf<Priority?>(Priority(name = note.priority.toString(), ReturnPrImageToInt(note.priority.toString())))
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        items(array) {

            Card(

                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedItem == it) Color.Transparent else Color.Transparent
                ),
                colors = CardDefaults.cardColors(containerColor = if (selectedItem == it) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface),
                onClick = {
                    selectedItem = it
                    selectedPriorityName.invoke(it.name)

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
                            text = it.name,
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
                            text = it.name,
                        )
                    }

                }
            }
        }
    }

}


@Composable
private fun DateTimeScreen(
    note: Note,
    sendSelectedDate: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.set(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        yearRange = 2020..2050
    )

    datePickerState.displayMode = DisplayMode.Picker

    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    var showDatePicker by remember { mutableStateOf(false) }


    Card(

        border = BorderStroke(width = 1.dp, color = Color.Transparent),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        onClick = {
            showDatePicker = true
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.event),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSecondary //else MaterialTheme.colorScheme.onSecondary
            )
            SpacerStd(width = 5)
            SmallText(text = note.dataTime2?: selectedDate, color = MaterialTheme.colorScheme.onSecondary)
            //Установите время окончания
        }
    }

    // date picker component
    if (showDatePicker) {
        DatePickerDialog(
            modifier = Modifier.scale(0.9f),
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = {
                        showDatePicker = false
                        sendSelectedDate.invoke(selectedDate)
                    }
                ) {
                    SmallText(text = "Ok", color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = {
                        showDatePicker = false
                    }
                ) { SmallText(text = "Отмена", color = MaterialTheme.colorScheme.onSecondary) }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onSurface,
            ),

            )
        {
            DatePicker(

                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.tertiary,

                    todayContentColor = MaterialTheme.colorScheme.tertiary,
                    todayDateBorderColor = MaterialTheme.colorScheme.tertiary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onTertiary,
                    dayContentColor = MaterialTheme.colorScheme.tertiary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.onSecondary,

                    headlineContentColor = MaterialTheme.colorScheme.tertiary,
                    weekdayContentColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }

}



private fun ReturnImageToInt(
    imgName: String
):Int {


    val clist =listOf(
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
    val img= clist.filter {
        imgName==it.name
    }
    try {
        Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image!!
    }catch (e:Exception){
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return 0
    }
}


private fun ReturnPrImageToInt(
    imgName: String
):Int {

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
    val img= array.filter {
        imgName==it.name
    }
    try {
        //Log.d(TAG, "ReturnImageToInt: ${img[0]}")
        return img[0].image!!
    }catch (e:Exception){
        Log.d(TAG, "ReturnImageToInt: ${e.message}")
        return 0
    }
}