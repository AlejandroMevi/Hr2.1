package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models

data class CalendarResponce(
    val codigo: String,
    val resultado: String,
    val calendario: List<Calendar>
)

data class Calendar(
    val claveCalendario: Int,
    val descripcionCalendario: String,
)
