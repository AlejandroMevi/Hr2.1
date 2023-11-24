package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models

data class DiasDescansosResponse(
    val codigo: String,
    val resultado: String,
    val diasDescanso: List<DiasDescansoItem>
)

data class DiasDescansoItem(
    val fecha: String,
    val marca: String
)

