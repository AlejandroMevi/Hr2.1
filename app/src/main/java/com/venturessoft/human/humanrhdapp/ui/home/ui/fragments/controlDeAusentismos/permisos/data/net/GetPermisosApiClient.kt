package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.GetPermisosResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface GetPermisosApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.ControlDeAusentismos.GETPERMISOS)
    suspend fun getPermisos(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long,
        @Query("anio") anio: Long,
        @Query("numEmp") numEmp: Long,
    ): GetPermisosResponse

}