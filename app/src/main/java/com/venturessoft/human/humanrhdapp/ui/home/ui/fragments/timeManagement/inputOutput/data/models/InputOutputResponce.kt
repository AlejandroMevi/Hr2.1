package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models

import java.io.Serializable
data class InputOutputResponce(
    val error: String = "",
    val codigo: String = "",
    val resultado: String = "",
    val slista:List<InputOutput> = listOf()
):Serializable
data class InputOutput(
    val fecha: String = "",
    val sec : Int = 0,
    val entrada: String = "",
    val salida: String = "",
    val estacion: String = "",
    val turno: String = "",
    val concepto: Int = 0,
    val vt1: String = "",
    val vt2: String = "",
    val vt3: String = "",
    val vt4: String = "",
    val departamento: String = "",
    val status: String = ""
): Serializable
