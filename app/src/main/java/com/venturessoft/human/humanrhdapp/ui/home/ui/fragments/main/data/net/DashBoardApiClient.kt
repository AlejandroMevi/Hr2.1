package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.DashBoardResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface DashBoardApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Login.DASHBOARD)
    suspend fun getDashBoard(
        @Header("Authorization") token: String,
        @Query("supervisor") supervisor: String,
        @Query("dias") dias: Int,
        @Query("numCia") numCia: Long
    ): DashBoardResponse
}