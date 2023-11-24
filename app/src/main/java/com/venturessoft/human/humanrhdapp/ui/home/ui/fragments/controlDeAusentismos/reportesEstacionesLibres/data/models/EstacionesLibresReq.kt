package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models

import java.io.Serializable

data class EstacionesLibresReq(
    val codigo: String,
    val resultado: String,
    val estacionesLibres: List<EstacionesLibresItem>
)

data class EstacionesLibresItem(
    val numEmp: Int,
    val empleado: String,
    val puesto: String,
    val fechaInicio: String,
    val hora: String,
    val bssid: String,
    val fotografia: String,
    val latitud: Double,
    val longitud: Double,
    val registro: String
) : Serializable
