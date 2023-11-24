package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models

data class AddStationRequest(
    var numCia: Long = 0,
    var numEmp: Long = 0,
    var gafete: Long = 0,
    var usuario: String = "",
    var estaciones:List<AddStations> = listOf()
)

data class AddStations(
    var estacion: String = "",
    var tipoEstacion: String = ""
)
