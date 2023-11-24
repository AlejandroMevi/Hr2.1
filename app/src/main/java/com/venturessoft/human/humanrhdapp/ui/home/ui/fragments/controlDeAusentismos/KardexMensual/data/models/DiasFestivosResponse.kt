package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models

data class DiasFestivosResponse(
    val codigo: String,
    val resultado: String,
    val diasFestivos: List<DiasFestivosItem>
)

data class DiasFestivosItem(
    val fecha: String,
    val marca: String
)

