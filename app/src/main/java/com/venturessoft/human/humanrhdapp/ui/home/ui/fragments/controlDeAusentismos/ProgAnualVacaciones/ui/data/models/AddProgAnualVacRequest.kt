package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models

import java.util.ArrayList

data class AddProgAnualVacRequest(
    var numCia: String,
    var numEmp: String,
    var fecha: ArrayList<String>,
    var usuario: String
)
