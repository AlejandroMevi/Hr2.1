package com.venturessoft.human.humanrhdapp.network.Response

data class GetMotivoPermisosResponse(
	val codigo: String? = null,
	val errorMessage: String? = null,
	val itemPermisos: List<ItemPermisosItem?>? = null,
	val error: Boolean? = null
)

data class ItemPermisosItem(
	val motivoPermiso: String? = null,
	val conceptoPermiso: Int? = null,
	val minutosPermiso: Int? = null,
	val numEmp: Int? = null,
	val horasPermiso: Int? = null
)

