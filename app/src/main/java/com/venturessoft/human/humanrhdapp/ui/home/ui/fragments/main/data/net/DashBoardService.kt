package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.DashBoardResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
class DashBoardService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun dashBoard(token: String, supervisor: String, dias: Int, numCia: Long): ApiResponceStatus<DashBoardResponse> {
        return makeNetworkCall {
            val response = autservice.create(DashBoardApiClient::class.java)
                .getDashBoard(token, supervisor, dias, numCia)
            response
        }
    }
}