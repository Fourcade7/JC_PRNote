@file:OptIn(ExperimentalMaterial3Api::class)

package com.pr7.jc_prnote.uiutils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarker
import com.pr7.jc_prnote.ui.screens.main.theme.BackgroundDarkerChild
import com.pr7.jc_prnote.ui.screens.main.theme.JC_PRNoteTheme
import com.pr7.jc_prnote.ui.screens.main.theme.Purple40
import kotlinx.coroutines.delay
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
            value = name,
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
fun CustomBasicTextFieldMulti(
    name: String = "",
    text: (String) -> Unit,
) {
    //for color change with material
    val localStyle = LocalTextStyle.current
    val mergedStyle =
        localStyle.merge(TextStyle(color = LocalContentColor.current, fontSize = 15.sp))
    //for color change with material
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = name,
        onValueChange = {

            text.invoke(it)
        },
        textStyle = mergedStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            ),
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true
    ) {
        TextFieldDefaults.DecorationBox(
            value = name,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            placeholder = { Text(text = stringResource(id = R.string.listitem))},
            interactionSource = interactionSource,
            // change the start padding
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            )
    }

}

@Composable
fun CustomBasicTextFieldDescription(
    name: String = "",
    checkError: Boolean = false,
    text: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    //for color change with material
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current, fontSize = 15.sp))
    //for color change with material

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }





    BasicTextField(
        value = name,
        onValueChange = {
            text.invoke(it)
        },
//            textStyle = TextStyle(
//                fontSize = 15.sp,
//                color = Color.White
//            ),
        //textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onPrimary, fontSize = 15.sp),
        textStyle = mergedStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(28.dp)
            )
            .focusRequester(focusRequester),
        interactionSource = interactionSource,
        enabled = true,
        singleLine = false,


        //for Hide Keyboard, but working without this action
        //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        //keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
    ) {
        TextFieldDefaults.DecorationBox(
            value = name,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            placeholder = {

                Text(
                    text = if (checkError) stringResource(id = R.string.deswrite) else stringResource(id = R.string.description),
                    color = if (checkError) Color.Red else MaterialTheme.colorScheme.onSecondary
                )
            },
            interactionSource = interactionSource,
            leadingIcon = {

                IconButton(
                    onClick = { /*TODO*/ },

                    ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.desc),
                        contentDescription = "Leading Icon"
                    )
                }

            },
            trailingIcon = {
                if (name.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            //name=""
                            text.invoke("")
                            focusManager.clearFocus()
                        }) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Close Icon"
                        )
                    }
                }
            },
            // change the start padding
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                //focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(15.dp),
        )
    }


//    LaunchedEffect(key1 = Unit) {
//        delay(100)
//        focusRequester.requestFocus()
//    }

}

@Composable
fun CustomBasicTextFieldWithIcon(
    name: String = "",
    checkError: Boolean = false,
    text: (String) -> Unit,

    ) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    //for color change with material
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current, fontSize = 15.sp))
    //for color change with material

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }



    JC_PRNoteTheme {
        BasicTextField(
            value = name,
            onValueChange = { text.invoke(it) },
//            textStyle = TextStyle(
//                fontSize = 15.sp,
//                color = Color.White
//            ),
            //textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onPrimary, fontSize = 15.sp),
            textStyle = mergedStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(28.dp)
                )
                .focusRequester(focusRequester),
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,


            //for Hide Keyboard, but working without this action
            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            //keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
        ) {
            TextFieldDefaults.DecorationBox(
                value = name,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                //isError = true,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    Text(
                        text = if (checkError) stringResource(id = R.string.titwrite) else stringResource(id =R.string.title),
                        color = if (checkError) Color.Red else MaterialTheme.colorScheme.onSecondary
                    )
                },
                interactionSource = interactionSource,
                leadingIcon = {

                    IconButton(
                        onClick = { /*TODO*/ },

                        ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.tag),
                            contentDescription = "Leading Icon"
                        )
                    }

                },
                trailingIcon = {
                    if (name.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                text.invoke("")
                                focusManager.clearFocus()
                            }) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = "Close Icon"
                            )
                        }
                    }
                },
                // change the start padding
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 1.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                shape = RoundedCornerShape(15.dp),
            )
        }
    }


//    LaunchedEffect(key1 = Unit) {
//        delay(100)
//        focusRequester.requestFocus()
//    }

}

@Composable
fun BasicTextFieldSearchCustom(
    name: String = "",
    text: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    // focusManager.moveFocus(FocusDirection.Next) //go to next textfield

    //for color change with material
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current))
    //for color change with material

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }




    JC_PRNoteTheme {
        BasicTextField(
            value = name,
            onValueChange = { text.invoke(it) },
//            textStyle = TextStyle(
//                fontSize = 15.sp,
//                color = Color.White
//            ),
            //textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onPrimary, fontSize = 15.sp),
            textStyle = mergedStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(28.dp)
                )
                .focusRequester(focusRequester),
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,


            //for Hide Keyboard, but working without this action
            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            //keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
        ) {
            TextFieldDefaults.DecorationBox(
                value = name,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                placeholder = { Text(text = stringResource(id = R.string.search)) },
                interactionSource = interactionSource,
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search Icon"
                        )
                    }
                },
                trailingIcon = {
                    if (name.isNotEmpty()) {
                        IconButton(onClick = {
                            text.invoke("")
                            focusManager.clearFocus()
                        }) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.close),
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
                    //focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    //focusedContainerColor =MaterialTheme.colorScheme.secondaryContainer,
                ),
                shape = RoundedCornerShape(15.dp),
            )
        }
    }


    LaunchedEffect(key1 = Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

}


@SuppressLint("SimpleDateFormat")
fun convertMillisToDateWithClock(millis: Long): String {
    //https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-date-format
    //val formatter = SimpleDateFormat("MMMM dd yyyy  HH:mm")
    val formatter = SimpleDateFormat("dd-MMMM HH:mm")
    return formatter.format(Date(millis))
}


@Composable
fun SpacerStd(width: Int = 0, height: Int = 0) {
    Spacer(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}

@Composable
fun LargeTextSemiBold(text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        textAlign = textAlign
    )
}

@Composable
fun LargeText(text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        textAlign = textAlign
    )
}

@Composable
fun MediumText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    color: Color = MaterialTheme.colorScheme.onSecondary
) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        color = color,
        textDecoration = textDecoration,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun SmallText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    color: Color = if (isSystemInDarkTheme()) BackgroundDarkerChild else BackgroundDarker,
    modifier: Modifier=Modifier
) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        textAlign = textAlign,
        textDecoration = textDecoration,
        color =  color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}



@Composable
fun DescriptionText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    color: Color = MaterialTheme.colorScheme.onSecondary
) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        textAlign = textAlign,
        textDecoration = textDecoration,
        color = color,
        lineHeight = 17.sp
        //maxLines = 1,
        //overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun CustomButton(text: String, clickable: () -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(15.dp),
        color = BackgroundDarker,
        onClick = {
            clickable.invoke()
        }


    ) {
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
    modifier: Modifier = Modifier,
    text: String,
    background: Color = BackgroundDarker,
    textColor: Color = BackgroundDarkerChild,
    icon:ImageVector,
    onClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = { Icon(imageVector = icon, "Extended floating action button.") },
        text = { Text(text = text) },
        containerColor = background,
        contentColor = textColor
    )
}


@Composable
fun CircularCheckbox(
    bgcolor: String?,
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = if (checked) if (bgcolor != null) BackgroundDarker else MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.1f
                ),

                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = if (bgcolor != null) BackgroundDarker else MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (!isSystemInDarkTheme() && bgcolor == null) BackgroundDarker else BackgroundDarkerChild,
                modifier = Modifier.size(19.dp)
            )
        }
    }
}

@Composable
fun CustomIcon(image: Int, color: Color = if (isSystemInDarkTheme()) BackgroundDarkerChild else BackgroundDarker,onClick: () -> Unit = {}) {

    Icon(
        painter = painterResource(id = image),
        contentDescription = "",
        tint = color,
        modifier = Modifier
            .size(20.dp)
            .clickable {
                onClick.invoke()
            }
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

        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp
            )
        },
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


@Composable
fun DropdownMenuPopup(
    expandDialog:Boolean=false
) {
    var expanded by remember { mutableStateOf(expandDialog) }
    //DIALOG
    if (expanded) {
        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
            DropdownMenu(
                expanded = expandDialog,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = (50).dp, y = (-40).dp),
                modifier = Modifier
                    .background(Color(0xFFE9E1F1))
                    .clip(RoundedCornerShape(16.dp))
            ) {


                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "search",
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(5.dp),
                                //tint =  EditButtonColor
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Редактировать",

                                fontSize = 13.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "search",
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(5.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Удалить",
                                fontSize = 13.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    },
                    onClick = {

                    }
                )
            }
        }
    }

    //DOALOG
}


