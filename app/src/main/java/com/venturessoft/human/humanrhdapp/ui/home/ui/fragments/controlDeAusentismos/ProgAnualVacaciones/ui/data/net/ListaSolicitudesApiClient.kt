package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ListaSolicitudesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ListaSolicitudesApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.ControlDeAusentismos.PROGANUALVACACIONES)
    suspend fun listaSolicitudes(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long,
        @Query("numEmp") numEmp: Long,
        @Query("anio") anio: Long
    ): ListaSolicitudesResponse
}