package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models

data class AddAusentismosRequest(
    var cia: Long, var numEmp: Long, var motivo: Long,
    var usuario: String, var status: String = "A", var fecha : List<String>
)
