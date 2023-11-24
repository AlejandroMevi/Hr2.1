package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.AddProgAnualVacRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ProgAnualVacResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddProgAnualVacApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.ControlDeAusentismos.PROGANUALVACACIONES)
    suspend fun addProgAnualVacacionesCA(
        @Header("Authorization") token: String,
        @Body addProgAnualVacRequest: AddProgAnualVacRequest
    ): ProgAnualVacResponse
}