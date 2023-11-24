package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models

data class DepartamentResponce(
    val error: String,
    val mensaje: String,
    val codigo: String,
    val resp: List<Departament>
)

data class Departament(
    val key: String,
    val value: String,
)


