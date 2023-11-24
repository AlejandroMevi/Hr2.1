package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models

data class GetPermisosResponse(
	val codigo: String? = null,
	val errorMessage: String? = null,
	val itemPermisos: List<ItemPermisosItem?>? = null,
	val error: Boolean? = null
)

data class ItemPermisosItem(
	var horaInicial: String? = null,
	var conceptoPermiso: Int? = null,
	var estatus: String? = null,
	var fechaPermiso: String? = null,
	var horaFinal: String? = null,
	var minutosPermiso: Int? = null,
	var concepto: String? = null,
	var observaciones: String? = null,
	var numEmp: Int? = null,
	var horasPermiso: Int? = null
)

