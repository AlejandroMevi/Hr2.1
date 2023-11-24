package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models

data class KardexAnualRequest(
    val cia: String,
    var numEmp: String,
    var anio: String,
    var mes : String? = null,
    var motivo: String
)
