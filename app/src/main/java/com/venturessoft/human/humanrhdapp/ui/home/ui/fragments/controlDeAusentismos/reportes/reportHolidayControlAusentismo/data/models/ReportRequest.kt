package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models

data class ReportRequest(
    var typeReport: String,
    var token: String? = null,
    var numEmp: List<Long>? = null,
    var cia: Long,
    var fechaTermino: String,
    var fechaInicio: String,
    var usuario: String,
    var origen: Boolean? = null,
    var retardos: Boolean? = null,
    var area: String? = null,
    var centro: String? = null,
    var linea: String? = null,
    var todosLosAnios: Boolean? = null,
    var programacion: Boolean? = null,
    var salidas: Boolean? = null,
    var supervisor: String? = null
    )