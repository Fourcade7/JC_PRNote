@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.ui.screens.add

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.TAG
import com.pr7.jc_prnote.data.model.Category
import com.pr7.jc_prnote.data.model.Priority
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
fun AddScreen(
    navHostController: NavHostController,
    addViewModel: AddViewModel
) {

    var mainTitle by remember {
        mutableStateOf("")
    }
    var descriptionTitle by remember {
        mutableStateOf("")
    }

    var selectedCategoryName by remember {
        mutableStateOf<String?>(null)
    }
    var selectedPriorityName by remember {
        mutableStateOf<String?>(null)
    }
    var selectedDate by remember {
        mutableStateOf<String?>(null)
    }
    var selectedColor by remember {
        mutableStateOf<String?>(null)
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

    var mList by remember {
        mutableStateOf(listOf<String>())
    }


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
            LargeTextSemiBold(text = "Add Note")
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
            AddMultiTask(addViewModel = addViewModel) { mList = it }
            Divider()
            AdditionalSettings(
                selectedCategoryTitle = { selectedCategoryName = it },
                selectedPriorityTitle = { selectedPriorityName = it },
                sendSelectedDate = { selectedDate = it },
                sendSelectedColor = { selectedColor = it }
            )

        }

        ExtendedFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = "Добавлять"
        ) {
            //ADD to Database
            if (mainTitle.isNotEmpty() && descriptionTitle.isNotEmpty()) {
                val key = System.currentTimeMillis().toString()
                addViewModel.addNote(
                    note = Note(
                        title = mainTitle,
                        description = descriptionTitle,
                        category = selectedCategoryName,
                        priority = selectedPriorityName,
                        key = key,
                        dataTime = today,
                        dataTime2 = selectedDate,
                        backgroundColor = selectedColor,

                        )
                )
                if (mList.isNotEmpty()) {
                    addViewModel.addMultiTask(
                        category = selectedCategoryName ?: "All",
                        key = key,
                        list = mList
                    )

                }

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
    addViewModel: AddViewModel,
    mTList: (List<String>) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var switcher by remember {
        mutableStateOf(false)
    }
    var openSettings by remember {
        mutableStateOf(false)
    }
    var multiTaskTitle by remember {
        mutableStateOf("")
    }

    var multitasksList by remember {
        mutableStateOf(
            mutableListOf(
                "Первый",
                "Второй"
            )
        )
    }
    val mlist = mutableListOf<String>()


    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallText(text = "Добавить список", color = MaterialTheme.colorScheme.onSecondary)
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
                                    text = "${index + 1}. $s",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                                SpacerStd(width = 3)
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = {
                                        mlist.remove(s)
                                        multitasksList =
                                            mutableListOf<String>().apply { addAll(mlist) }
                                        mTList.invoke(multitasksList)
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
                        mlist.add(multiTaskTitle)
                        multitasksList = mutableListOf<String>().apply { addAll(mlist) }
                        multiTaskTitle = ""

                        mTList.invoke(multitasksList)


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
    selectedCategoryTitle: (String) -> Unit,
    selectedPriorityTitle: (String) -> Unit,
    sendSelectedDate: (String) -> Unit,
    sendSelectedColor: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var switcher by remember {
        mutableStateOf(false)
    }
    var openSettings by remember {
        mutableStateOf(false)
    }





    Column(

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallText(text = "Дополнительные настройки", color = MaterialTheme.colorScheme.onSecondary)
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

                SmallText(text = "Выберите категорию", color = MaterialTheme.colorScheme.tertiary)
                SpacerStd(height = 1)
                CategoryScreen { selectedCategoryTitle.invoke(it) }
                Divider()
                SpacerStd(height = 1)
                SmallText(text = "Выберите даты", color = MaterialTheme.colorScheme.tertiary)
                DateTimeScreen { sendSelectedDate.invoke(it) }
                Divider()
                SpacerStd(height = 8)
                SmallText(text = "Выбрать цвет фона", color = MaterialTheme.colorScheme.tertiary)
                SpacerStd(height = 8)
                SelectColorScreen() { sendSelectedColor.invoke(it) }

                SpacerStd(height = 8)
                Divider()
                SmallText(text = "Приоритет", color = MaterialTheme.colorScheme.tertiary)
                PriorityScreen { selectedPriorityTitle.invoke(it) }
                SpacerStd(height = 8)


            }
        }

    }

}


@Composable
private fun CategoryScreen(
    selectedCategoryName: (String) -> Unit
) {

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
                    selectedCategoryName.invoke(it.name)

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
private fun PriorityScreen(
    selectedPriorityName: (String) -> Unit
) {

    val array = remember {
        listOf(
            Priority(name = "Важное", image = R.drawable.rocket),
            Priority(name = "Высокий", image = R.drawable.bomb),
            Priority(name = "Средний", image = R.drawable.gauge),
            Priority(name = "Низкий", image = R.drawable.convenient),
        )
    }


    var selectedItem by remember {
        mutableStateOf<Priority?>(null)
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
private fun DateTimeScreen(
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
        yearRange = 2020..2025
    )

    datePickerState.displayMode = DisplayMode.Picker

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
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
            SmallText(text = selectedDate, color = MaterialTheme.colorScheme.onSecondary)
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