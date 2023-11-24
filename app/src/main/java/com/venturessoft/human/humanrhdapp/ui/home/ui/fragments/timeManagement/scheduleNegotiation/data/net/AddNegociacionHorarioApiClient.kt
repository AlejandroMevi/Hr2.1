package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.net

import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.AddInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationResponce
import retrofit2.http.*

interface AddNegociacionHorarioApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.GETNEGOCIACIONHORARIO)
    suspend fun getScheduleNegotiation(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long,
        @Query("numEmp") numEmp: Long,
        @Query("mes") mes: Int,
        @Query("anio") anio: Int,
    ): ScheduleNegotiationResponce

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.AdministracionDeTiempos.POSTNEGOCIACIONHORARIO)
    suspend fun postScheduleNegotiation(
        @Header("Authorization") token: String,
        @Body scheduleNegotiationRequest: ScheduleNegotiationRequest
    ): GeneralResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("${URL.AdministracionDeTiempos.PUTNEGOCIACIONHORARIO}/{cia}/{numEmp}/{fechaAplicacion}")
    suspend fun putScheduleNegotiation(
        @Header("Authorization") token: String,
        @Path("cia") cia: Long,
        @Path("numEmp") numEmp: Long,
        @Path("fechaAplicacion") fechaAplicacion : String,
        @Body scheduleNegotiationRequest: ScheduleNegotiationRequest
    ): GeneralResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @DELETE("${URL.AdministracionDeTiempos.DELETENEGOCIACIONHORARIO}/{cia}/{numEmp}/{fechaAplicacion}")
    suspend fun deleteScheduleNegotiation(
        @Header("Authorization") token: String,
        @Path("cia") cia: Long,
        @Path("numEmp") numEmp: Long,
        @Path("fechaAplicacion") fechaAplicacion : String
    ): GeneralResponse
}