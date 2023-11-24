package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GetDiasFestivosApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.Kardex.DIASFESTIVOS)
    suspend fun getDiasFestivos(
        @Header("Authorization") token: String,
        @Body diasFestivosRequest: DiasFestivosRequest
    ): DiasFestivosResponse

}