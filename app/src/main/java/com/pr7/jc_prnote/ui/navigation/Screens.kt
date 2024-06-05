package com.pr7.jc_prnote.ui.navigation



sealed class Screens(val route:String){

    object Splash:Screens(route = "splash_screen")
    object Home:Screens(route = "home_screen")
    object OnBoard:Screens(route = "onboard_screen")
    object Add:Screens(route = "add_screen")

}