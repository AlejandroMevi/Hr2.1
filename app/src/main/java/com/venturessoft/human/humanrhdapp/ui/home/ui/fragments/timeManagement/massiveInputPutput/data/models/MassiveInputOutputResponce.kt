package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.data.models

import java.io.Serializable
data class MassiveInputOutputResponce(
    val error: String = "",
    val codigo: String = "",
    val resultado: String = "",
    val slista:List<MassiveInputOutput> = listOf()
):Serializable

data class MassiveInputOutput(
    val numEmple:String = "",
    val nombreCompleto:String = "",
    val puesto:String = "Desarrollador SR",
    val foto:String = "",
    val fueraTurno: Int = 0,
    val isAcctive : Boolean = true,
    val fecha:String = "27/10/2023",
    val entrada: String = "09:00",
    val salida: String = "18:00",
    val estacion: String = "Estación Venturessoft",
    val secuencia: String = "1",
    val tipoRegistro: List<Int> = listOf(0),
    val retardos: String = "0",
    val zona: String = "15",
    val departamento: String = "00002--"
): Serializable
