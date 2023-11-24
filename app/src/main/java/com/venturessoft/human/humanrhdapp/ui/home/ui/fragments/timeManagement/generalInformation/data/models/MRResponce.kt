package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models

data class MRResponce(
    val error: String = "",
    val codigo: String = "",
    val resultado: String = "",
    val listaMaestroReloj: List<ListaMaestroReloj>
)
data class ListaMaestroReloj(
    val fechaAplicacion: String = "",
    val gafete: Long = 0,
    val calendario: Int = 0,
    val rolHorario: Int = 0,
    val turno: Int = 0,
    val dom: String = "",
    val lun: String = "",
    val mar: String = "",
    val mie: String = "",
    val jue: String = "",
    val vie: String = "",
    val sab: String = "",
    val cond1: String = "",
    val cond2: String = "",
    val cond3: String = "",
    val cond4: String = "",
    val cond5: String = "",
    val cond6: String = "",
    val cond7: String = "",
    val cond8: String = "",
    val cond9: String = "",
    val cond10: String = "",
    val status: String = ""
):java.io.Serializable

