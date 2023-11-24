package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models

data class FreeStationResponse(
	val codigo: String,
	var estacionesEmp: List<EstacionesEmpItem?>? = null
)

data class EstacionesEmpItem(
	var fechaChecada: String? = null,
	var horaChecada: String? = null,
	var numEmp: Int? = null,
	var estacion: String? = null,
	var numCia: Int? = null,
	var nombreEmp: String? = null,
	var tipo: String? = null
)

