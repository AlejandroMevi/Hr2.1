package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models

import java.io.Serializable
data class AddPreautorizacionTiemposResponse(
    val error: Boolean,
    val codigo: String,
    val mensaje: String,
    val stiempoExtra:List<TiempoExtra>
)
data class TiempoExtra(
    var fecha:String,
    var horaEntrada:String,
    var horaSalida:String,
    var horas:String,
    var idCategoria:String,
    var categoria:String,
    var idZona:Long,
    var zona:String,
    var idDepartamento:String,
    var departamento:String,
    var isExpanded:Boolean = false
):Serializable

