package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.net

import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.network.URL.AdministracionDeTiempos.Companion.ADDSTATIONS
import com.venturessoft.human.humanrhdapp.network.URL.AdministracionDeTiempos.Companion.GETSTATIONS
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.AddStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.EditStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.InfoGenralRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.MRResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.StationResponce
import retrofit2.http.*

interface GeneralInfoApi {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.MR)
    suspend fun getMR(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long,
        @Query("numEmp") numEmp: Long,
    ): MRResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.AdministracionDeTiempos.ADDINFORMACIONGENERAL)
    suspend fun addAdministrarMR(
        @Header("Authorization") token: String,
        @Body infoGenralRequest: InfoGenralRequest,
    ): GeneralResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("HumaneLand/RHD/updateAdministrarMR/{cia}/{numEmp}/{fechaAplicacion}/{usuario}")
    suspend fun editAdministrarMR(
        @Header("Authorization") token: String,
        @Path("cia") cia: Long,
        @Path("numEmp") numEmp: Long,
        @Path("fechaAplicacion") fechaAplicacion : String,
        @Path("usuario") usuario:String,
        @Body infoGenralRequest: InfoGenralRequest,
    ): GeneralResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(GETSTATIONS)
    suspend fun getStations(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long,
        @Query("numEmp") numEmp: Long,
        @Query("gafete") gafete: Long
    ): StationResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(ADDSTATIONS)
    suspend fun addStations(
        @Header("Authorization") token: String,
        @Body addStationRequest: AddStationRequest,
    ): GeneralResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("updateEstaciones/{cia}/{numEmp}/{gafete}/{usuario}")
    suspend fun editStations(
        @Header("Authorization") token: String,
        @Path("cia") cia: Long,
        @Path("numEmp") numEmp: Long,
        @Path("gafete") gafete : Long,
        @Path("usuario") usuario:String,
        @Body editStationRequest: List<EditStationRequest>,
    ): GeneralResponse
}