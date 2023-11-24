package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models

data class StationResponce(
    var error: Boolean = false,
    var codigo: String = "",
    var resultado: String = "",
    var estaciones: List<Station> = listOf(),
)
data class Station(
    var id:Int = 0,
    var estacion:String = "",
    var descripcion:String = "",
    var tipo:String = "",
    var gafete:Long = 0,
    var status:String = "B",
    var service:Int = 0
)
