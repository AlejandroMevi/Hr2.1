package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KardexAnualApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.Kardex.KARDEXANUAL)
    suspend fun getKardexAnual(
        @Header("Authorization") token: String,
        @Body kardexAnualRequest: KardexAnualRequest
    ): KardexAnualResponse
}