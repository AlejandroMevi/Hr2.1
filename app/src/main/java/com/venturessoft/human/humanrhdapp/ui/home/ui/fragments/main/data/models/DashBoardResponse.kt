package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models

data class DashBoardResponse(
	val codigo: String? = null,
	val errorMessage: String? = null,
	val entradasSalidas: List<EntradasSalidasItem?>? = null,
	val error: Boolean? = null
)

data class EntradasSalidasItem(
	val fecha: String? = null,
	val total: Float? = null,
	val descanso: Float? = null,
	val asistencia: Float? = null,
	val incapacidad: Float? = null,
	val falta: Float? = null,
	val vacaciones: Float? = null,
	val ausentismo: Float? = null,
	val festivo: Float? = null
)

