package com.pr7.jc_prnote.ui.screens.select_color

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.pr7.jc_prnote.data.local.room.Note
import kotlin.random.Random


@SuppressLint("MutableCollectionMutableState")
@Composable
fun SelectColorScreen(
    note: Note?=null,
    sendSelectedColor:(String)->Unit
) {

    val colors by remember {
        mutableStateOf(
            mutableListOf(
                "#cc0000",
                "#ff8000",
                "#59b300",
                "#009999",
                "#6666ff",
                "#cc33ff",
                "#d147a3"
            )
        )
    }

    var selectedColor by remember{
        mutableStateOf<String?>(note?.backgroundColor)
    }

//    (1..20).map {
//        colors.add(randomColor())
//    }
    
    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        itemsIndexed(colors){index: Int, item: String ->
            LazyItem(color = item, showIcon = selectedColor==item){
                selectedColor=item
                sendSelectedColor.invoke(item)
            }
        }
    }





}


@Composable
private fun LazyItem(color: String,showIcon:Boolean,onClick:()->Unit) {

    
    Column(
        modifier = Modifier

            .clip(CircleShape)
            .background(Color(color.toColorInt()))
            .size(40.dp)
            .clickable {
                       onClick.invoke()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showIcon){
            Column(
                modifier = Modifier

                    .clip(CircleShape)
                    .border(width = 7.dp,color= Color.White, shape = CircleShape)
                    .background(Color(color.toColorInt()))
                    .size(35.dp)

            ) {

            }
        }else{
            Column(
                modifier = Modifier

                    .clip(CircleShape)
                    .border(width = 1.dp,color= Color.White, shape = CircleShape)
                    .background(Color(color.toColorInt()))
                    .size(40.dp)

            ) {

            }
        }
    }
    


}

fun randomColor() = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    )