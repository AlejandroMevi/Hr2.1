package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models

import java.io.Serializable

data class VacationAutthorizationRequest(
    val codigo: String,
    val resultado: String,
    val diasDescanso: List<VacationAutthorizationItem>
)

data class VacationAutthorizationItem(
    val numEmp:Int,
    val empleado: String,
    val fechaInicio: String,
    val fechaTermino: String,
    val pagoAnticipado: String,
    val pagoSecuencia: String,
    val dias: String,
    val fotografia:String,
    val secuenciasProgramadas: SecuenciasProgramadas,
    val saldoVacaciones: SaldoVacaciones
):Serializable

data class SecuenciasProgramadas(
    val fechaInicio: String,
    val fechaTermino: String,
    val dias: String,
    val anio: String,
)

data class SaldoVacaciones(
    val periodo: String,
    val dias: String,
    val diasAdicionales: String,
    val fechaVencimiento: String,
)

