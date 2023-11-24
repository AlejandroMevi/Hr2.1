package com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models

data class GetCatalogoTurnoResponse(
    val codigo: String? = null,
    val resultado: String? = null,
    val turno: List<TurnoItem?>? = null
)

data class TurnoItem(
    val descViernes: String? = null,
    val descripcionTurno: String? = null,
    val descMartes: String? = null,
    val descLunes: String? = null,
    val descMiercoles: String? = null,
    val descDomingo: String? = null,
    val descJueves: String? = null,
    val claveTurno: Int? = null,
    val descSabado: String? = null
)

