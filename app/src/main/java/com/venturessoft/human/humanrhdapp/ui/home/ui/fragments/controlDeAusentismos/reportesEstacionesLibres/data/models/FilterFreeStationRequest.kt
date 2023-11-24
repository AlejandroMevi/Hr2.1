package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models

data class FilterFreeStationRequest(
    var fechaInicio: String?=null,
    var fechaFin: String?=null,
    var empls: List<Long>?=null,
)
