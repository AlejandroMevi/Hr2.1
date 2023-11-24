package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models

data class DiasDescansosRequest(
    val cia: String,
    var anio: String,
    var mes: String? = null,
    var numEmp: String
)
