package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionTiemposResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreaturoizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreautResponse
import retrofit2.http.*

interface AddPreauTiemposApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.PREAUTORIZACIONTIEMPOS)
    suspend fun preautorizacionTiempos(
        @Header("Authorization") token: String,
        @Query("cia") cia: Int,
        @Query("numEmp") numEmp: Int,
        @Query("mes") mes: Int,
        @Query("anio") anio: Int
    ) : AddPreautorizacionTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.AdministracionDeTiempos.ADDPREAUTORIZACIONTIEMPOS)
    suspend fun addPreautorizacionTiempos(
        @Header("Authorization") token: String,
        @Body addPreautorizacionRequest: AddPreautorizacionRequest
    ) : AddAusentismosResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT(URL.AdministracionDeTiempos.EDITPREAUTORIZACIONTIEMPOS)
    suspend fun editPreautorizacionTiempos(
        @Header("Authorization") token: String,
        @Body editPreaturoizacionRequest: EditPreaturoizacionRequest
    ) : EditPreautResponse
}