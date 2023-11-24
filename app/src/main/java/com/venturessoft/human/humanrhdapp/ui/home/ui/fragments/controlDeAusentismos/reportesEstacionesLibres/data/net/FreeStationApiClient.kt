package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsEstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsFreeStationResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesEmpResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.FreeStationResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface FreeStationApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.FreeStation.FREE)
    suspend fun freeStation(
        @Body estacionesLibresRequest: EstacionesLibresRequest,
        @Header("Authorization") token: String
    ): FreeStationResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.FreeStation.FREEDETAILS)
    suspend fun detailsFreeStation(
        @Body detailsEstacionesLibresRequest: DetailsEstacionesLibresRequest,
        @Header("Authorization") token: String
    ): DetailsFreeStationResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.FreeStation.TOKEN)
    suspend fun token(
        @Query("token") token: String
    ): TokenResponse
}