package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.net

import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.models.GetVacacionesResponse
import com.venturessoft.human.humanrhdapp.network.URL
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface GetVacacionesApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.ControlDeAusentismos.VACACIONES)
    suspend fun getVacacionesCA(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long, @Query("numEmp") numEmp: Long, @Query("anio") anio: Int
    ): GetVacacionesResponse

}