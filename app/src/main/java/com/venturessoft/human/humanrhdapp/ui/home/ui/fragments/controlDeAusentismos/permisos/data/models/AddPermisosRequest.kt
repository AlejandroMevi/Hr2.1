package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models

data class AddPermisosRequest(var cia : String, var numEmp : String, var conceptoPermiso : String,
                              var fechaPermiso : String, var horaInicial : String, var horaFinal : String,
                              var minutosPermiso : String, var horasPermiso : String, var estatus : String,
                              var observaciones : String, var usuario : String)
