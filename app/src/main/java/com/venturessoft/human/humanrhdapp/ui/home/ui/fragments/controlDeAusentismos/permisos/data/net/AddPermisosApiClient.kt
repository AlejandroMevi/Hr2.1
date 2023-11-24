package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net

import com.venturessoft.human.humanrhdapp.network.Response.AddPermisosResponse
import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.AddPermisosRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddPermisosApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.ControlDeAusentismos.ADDPERMISOS)
    suspend fun getAddPermisosCA(
        @Header("Authorization") token: String,
        @Body addPermisosRequest: AddPermisosRequest
    ): AddPermisosResponse
}