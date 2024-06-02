@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.uiutils

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.ui.screens.main.theme.Purple40
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun BasicTextFieldCustom() {
    val interactionSource = remember { MutableInteractionSource() }
    var name by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = name,
        onValueChange = { name = it },
        textStyle = MaterialTheme.typography.titleSmall,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(28.dp)
            ),
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true
    ) {
        TextFieldDefaults.DecorationBox(
            value =name,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            placeholder = { Text(text = "enter your name") },
            interactionSource = interactionSource,
            // change the start padding
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )
    }

}

@Composable
fun BasicTextFieldSearchCustom() {

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    var name by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = name,
        onValueChange = { name = it },
        textStyle = TextStyle(
            fontSize = 15.sp,
            //color = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(28.dp)
            ),
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true,

        //for Hide Keyboard, but working without this action
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
    ) {
        TextFieldDefaults.DecorationBox(
            value =name,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            placeholder = { Text(text = "enter your name") },
            interactionSource = interactionSource,
            leadingIcon = {
              IconButton(onClick = { /*TODO*/ }) {
                  Icon(
                      modifier = Modifier.size(30.dp),
                      painter = painterResource(id =R.drawable.search),
                      contentDescription = "Search Icon"
                  )
              }
            },
            trailingIcon = {
                if (name.isNotEmpty()) {
                    IconButton(onClick = {
                        name=""
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            modifier = Modifier.size(17.dp),
                            painter = painterResource(id =R.drawable.close),
                            contentDescription = "Close Icon"
                        )
                    }
                }
            },
            // change the start padding
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp),

        )
    }

}




fun convertMillisToDate(millis: Long): String {
    //https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-date-format
    //val formatter = SimpleDateFormat("MMMM dd yyyy  HH:mm")
    val formatter = SimpleDateFormat("dd-MMM")
    return formatter.format(Date(millis))
}


@Composable
fun SpacerStd(width:Int=0,height:Int=0) {
    Spacer(modifier = Modifier
        .width(width.dp)
        .height(height.dp))
}

@Composable
fun LargeTextSemiBold(text:String ,textAlign: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        textAlign = textAlign
    )
}

@Composable
fun LargeText(text:String ,textAlign: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        textAlign = textAlign
    )
}

@Composable
fun MediumText(text:String, textAlign: TextAlign = TextAlign.Start, fontWeight: FontWeight = FontWeight.Normal, textDecoration: TextDecoration = TextDecoration.None) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        textDecoration = textDecoration
    )
}

@Composable
fun SmallText(text:String, textAlign: TextAlign = TextAlign.Start, textDecoration: TextDecoration = TextDecoration.None) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        textAlign = textAlign,
        textDecoration = textDecoration
    )
}




@Composable
fun CustomButton(text: String,clickable:()->Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(15.dp),
        color = Purple40,
        onClick = {
            clickable.invoke()
        }


    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SmallText(text = text)
        }
    }

}


@Composable
fun ExtendedFAB(
    modifier: Modifier=Modifier,
    text: String,
    background: Color= Purple40,
    textColor: Color= Color.White,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = text) },
        containerColor = background,
        contentColor = textColor
    )
}






@ExperimentalMaterial3Api
@Composable
fun CustomTextField(
    text: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var textfieldname by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = textfieldname,
        onValueChange = {
            textfieldname = it
            text.invoke(it)
        },
        enabled = true,

        placeholder = { Text(
            text = placeholder,
            fontSize = 14.sp
        ) },
        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = Purple40,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Transparent,
            unfocusedBorderColor = Color(0xFF868588),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedPlaceholderColor = Color(0xFF868588),

            ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.splashicon),
//                    contentDescription = "copy",
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Black
//                )
//            }

        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp
        )


    )

}


