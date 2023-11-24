package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models

data class GetListaAusentismosResponse(
	val codigo: String? = null,
	val mensaje: String? = null,
	val error: Boolean? = null,
	val itemAusentismos: List<ItemAusentismosItem?>? = null
)

data class ItemAusentismosItem(
	val motivo: String? = null,
	val fechaInicial: String? = null,
	val motivoDescripcion: String? = null,
	val estatus: String? = null,
	val dias: Int? = null,
	val numEmp: Int? = null
)

