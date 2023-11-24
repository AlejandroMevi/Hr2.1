package com.venturessoft.human.humanrhdapp.ui.login.data.net

import com.venturessoft.human.humanrhdapp.ui.login.data.models.ForgotPasswordResponse
import com.venturessoft.human.humanrhdapp.ui.login.data.models.LoginResponse
import com.venturessoft.human.humanrhdapp.network.URL
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LoginApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Login.LOGIN)
    suspend fun getLogin(
        @Query("usuario") usuario: String,
        @Query("password") password: String
    ): LoginResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Login.OLVIDE_CONTRASENA)
    suspend fun getPasswordService(@Query("userId") userId: String) : ForgotPasswordResponse
}