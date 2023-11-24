package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models

data class KardexResponse(
    val kardexMensual: List<KardexMensualItem?>? = null
)

data class KardexMensualItem(
    var numEmp: Int? = null,
    var marcas: List<String?>? = null,
    var nomEmp: String? = null,
    var fotoEmp: String? = null
)

