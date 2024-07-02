

package com.pr7.jc_prnote.ui.navigation

import androidx.room.ColumnInfo
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.Note
import kotlinx.serialization.Serializable


const val PASS_STRING="text"
const val PASS_INT="number"

@Serializable
sealed class Screens(val route:String){

    object Splash:Screens(route = "splash_screen")

    @Serializable
    object Home:Screens(route = "home_screen")
    object OnBoard:Screens(route = "onboard_screen")
    object Add:Screens(route = "add_screen")
    @Serializable
    data class Update(
        val uid: Int=0,
        val title: String?=null,
        val description:String?=null,
        val dataTime:String?=null,
        val dataTime2:String?=null,
        val priority:String?=null,
        val category:String?=null,
        val key:String?=null,
        val backgroundColor:String?=null,
    ):Screens(route = "update_screen")

    object Settings:Screens(route = "settings_screen")

}