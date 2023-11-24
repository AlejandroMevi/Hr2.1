package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net

import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.kardexReporteRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.KardexResponse
import com.venturessoft.human.humanrhdapp.network.URL
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
interface KardexReporteApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST(URL.Kardex.KARDEX)
    suspend fun getKardex(
        @Header("Authorization") token: String,
        @Body kardexReporteRequest: kardexReporteRequest
    ): KardexResponse
}