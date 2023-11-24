package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models

data class ScheduleNegotiationResponce(
    var error:Boolean,
    var codigo: String,
    var resultado: String,
    var slista: List<ScheduleNegotiation>,
)
data class ScheduleNegotiation(
    var fechaInicio: String,
    var fechaFin: String,
    var idRol: Int,
    var rolTurno: String,
    var idTurno: Int,
    var turno: String,
    var dom: String,
    var lun: String,
    var mar: String,
    var mie: String,
    var jue: String,
    var vie: String,
    var sab: String,
    var idCategoria: String,
    var categoria: String,
    var idZona: Long,
    var zona: String,
    var idDepartamento: String,
    var departamento: String,
    var auditoria: String
):java.io.Serializable
