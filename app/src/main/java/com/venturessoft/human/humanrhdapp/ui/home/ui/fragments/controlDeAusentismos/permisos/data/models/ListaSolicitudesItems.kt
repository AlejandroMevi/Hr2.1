package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models

data class ListaSolicitudesItems(
    var concepto: String? = null,
    var conceptoPermiso: Int? = null,
    var dias: Int? = null,
    var estatus: String? = null,
    var fechaPermiso: String? = null,
    var fechaInicial: String? = null,
    var horasPermiso: Int? = null,
    var horaInicial: String? = null,
    var horaFinal: String? = null,
    var motivoDescripcion: String? = null,
    var minutosPermiso: Int? = null,
    var motivo: String? = null,
    var numEmp: Int? = null,
    var observaciones: String? = null
)
