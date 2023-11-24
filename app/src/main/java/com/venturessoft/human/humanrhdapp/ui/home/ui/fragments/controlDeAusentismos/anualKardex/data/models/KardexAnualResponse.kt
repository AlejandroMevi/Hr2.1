package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models

data class KardexAnualResponse(
    val codigo: String,
    val resultado: String,
    val skardexAnual: SkardexAnual
)

data class SkardexAnual(
    val marca: List<MarcaItem>,
    val numEmp: Int
)

data class MarcaItem(
    val fecha: String,
    val marca: String
)

