package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models

class ItemsBusqueda (
    val totalRegistros: Int = 0,
    val item: List<ItemItemBusqeda> = listOf(),
    val paginaActual: Int = 0,
    val last: Boolean = false,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
    val totalPaginas: Int = 0,
    val first: Boolean = false
)