package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.models.AddVacacionesRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.models.AddVacacionesResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddVacacionesApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.ControlDeAusentismos.VACACIONES)
    suspend fun addVacacionesCA(
        @Header("Authorization") token: String,
        @Body AddVacacionesRequest: AddVacacionesRequest
    ): AddVacacionesResponse

}