package com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models

data class GetCatalogoRolResponse(
    val codigo: String? = null,
    val resultado: String? = null,
    val rolHorario: List<RolHorarioItem?>? = null
)

data class RolHorarioItem(
    val descripcionRolHorario: String? = null,
    val claveRolHorario: Int? = null
)

