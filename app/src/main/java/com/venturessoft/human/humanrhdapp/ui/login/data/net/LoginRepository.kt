package com.venturessoft.human.humanrhdapp.ui.login.data.net

import com.venturessoft.human.humanrhdapp.ui.login.data.models.ForgotPasswordResponse
import com.venturessoft.human.humanrhdapp.ui.login.data.models.LoginResponse
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
class LoginRepository {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun login(email: String, password: String): ApiResponceStatus<LoginResponse> {
        return makeNetworkCall {
            val response = autservice.create(LoginApiClient::class.java).getLogin(email, password)
            evaluateResponce(response.codigo.toString())
            response
        }
    }

    suspend fun reportAsisOrgAt(email: String): ApiResponceStatus<ForgotPasswordResponse> {
        return makeNetworkCall {
            val response = autservice.create(LoginApiClient::class.java).getPasswordService(email)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
}