package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models

import java.io.Serializable

data class ListItemsModel(
    var opcion: Int = 0,
    var concepto: String = "null",
    var conceptoPermiso: Int = 0,
    var dias: Int = 0,
    var estatus: String = "",
    var fechaPermiso: String = "",
    var fechaInicial: String = "",
    var horasPermiso: Int = 0,
    var horaInicial: String = "",
    var horaFinal: String = "",
    var motivoDescripcion: Int = 0,
    var minutosPermiso: Int = 0,
    var motivo: String = "",
    var numEmp: Int = 0,
    var observaciones: String = ""
): Serializable
