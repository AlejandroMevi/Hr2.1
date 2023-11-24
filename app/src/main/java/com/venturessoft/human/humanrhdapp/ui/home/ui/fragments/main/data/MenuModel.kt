package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data

import java.io.Serializable
data class MenuModel(
    var id:Int = 0,
    var name:String = "",
    var resource:Int = 0,
    var puesto: String = "",
    var numEmp: String = "0",
    var nombreCompleto: String = "",
    var fotografia: String = ""
): Serializable
