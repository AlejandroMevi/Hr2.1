package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AusentismosApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.ControlDeAusentismos.ADDAUSENTISMOS)
    suspend fun getAddAusentismos(
        @Header("Authorization") token: String,
        @Body addAusentismosRequest: AddAusentismosRequest
    ): AddAusentismosResponse
}