package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.net

import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.AddInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.EditInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputResponce
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EntradasSalidasApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.AdministracionDeTiempos.GETENTRADASSALIDAS)
    suspend fun getInputOutput(
        @Header("Authorization") token: String,
        @Body inputOutputRequest: InputOutputRequest
    ): InputOutputResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.AdministracionDeTiempos.ADDENTRADASSALIDAS)
    suspend fun addInputOutput(
        @Header("Authorization") token: String,
        @Body addInputOutputRequestRequest: AddInputOutputRequest
    ): GeneralResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @DELETE("HumaneLand/RHD/EntradasSalidas/{cia}/{numEmp}/{fecha}/{sec}/{usuario}")
    suspend fun deleteInputOutput(
        @Header("Authorization") token: String,
        @Path("cia") cia:String,
        @Path("numEmp") numEmp:String,
        @Path("fecha") fecha:String,
        @Path("sec") sec:String,
        @Path("usuario") usuario:String
    ): GeneralResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("HumaneLand/RHD/EntradasSalidas/{cia}/{numEmp}/{fecha}/{sec}")
    suspend fun editInputOutput(
        @Header("Authorization") token: String,
        @Path("cia") cia:String,
        @Path("numEmp") numEmp:String,
        @Path("fecha") fecha:String,
        @Path("sec") sec:String,
        @Body editInputOutputRequest: EditInputOutputRequest
    ): GeneralResponse
}