package com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models

data class CatalogResponse(
    val codigo: String = "",
    val resp: List<Resp> = listOf(),
    val mensaje: String = "",
    val error: Boolean = false
)

data class Resp(
    val value: String = "",
    val key: String = ""
)
