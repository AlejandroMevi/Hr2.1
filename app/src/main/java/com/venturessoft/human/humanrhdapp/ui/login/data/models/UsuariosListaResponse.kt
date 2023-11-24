package com.venturessoft.human.humanrhdapp.ui.login.data.models

import com.google.gson.annotations.SerializedName

data class UsuariosListaResponse(

	@field:SerializedName("codigo")
	val codigo: String? = null,

	@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("items")
	val items: Items? = null
)

data class Items(

	@field:SerializedName("totalRegistros")
	val totalRegistros: Int? = null,

	@field:SerializedName("item")
	val item: List<ItemItemList?>? = null,

	@field:SerializedName("paginaActual")
	val paginaActual: Int? = null,

	@field:SerializedName("last")
	val last: Boolean? = null,

	@field:SerializedName("hasPrevious")
	val hasPrevious: Boolean? = null,

	@field:SerializedName("hasNext")
	val hasNext: Boolean? = null,

	@field:SerializedName("totalPaginas")
	val totalPaginas: Int? = null,

	@field:SerializedName("first")
	val first: Boolean? = null
)

data class ItemItemList(

	@field:SerializedName("puesto")
	var puesto: String? = null,

	@field:SerializedName("numEmp")
	var numEmp: String? = null,

	@field:SerializedName("nombreCompleto")
	var nombreCompleto: String? = null,

	@field:SerializedName("fotografia")
	var fotografia: String? = null
)
