package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GetDiasDescansosApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.Kardex.DIASDESCANSOS)
    suspend fun getDiasDescansos(
        @Header("Authorization") token: String,
        @Body diasDescansosRequest: DiasDescansosRequest
    ): DiasDescansosResponse
}