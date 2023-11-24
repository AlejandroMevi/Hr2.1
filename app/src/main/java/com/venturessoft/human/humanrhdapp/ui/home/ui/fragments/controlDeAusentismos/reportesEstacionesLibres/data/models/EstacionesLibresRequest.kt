package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models

data class EstacionesLibresRequest(
    var numCia: String,
    var idUsuario: String,
    var empls: List<Long>? = null,
    var fechaInicio: String? = null,
    var fechaFin: String? = null,
    )
