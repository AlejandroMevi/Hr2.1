package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models

data class ListaSolicitudesResponse(
	val vacacionesProgramadas: List<VacacionesProgramadasItem?>? = null,
	val codigo: String? = null,
	val mensaje: String? = null,
	val error: Boolean? = null
)

data class VacacionesProgramadasItem(
	var fechaInicio: String? = null,
	var fechaTermino: String? = null,
	var grupo: Int? = null,
	var secuencia: Int? = null,
	var diasTomados: Int? = null
)

