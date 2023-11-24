package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.AddInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.EditInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputResponce
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class EntradasSalidasService {
    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun getInputOutput(inputOutputRequest: InputOutputRequest): ApiResponceStatus<InputOutputResponce> =
        makeNetworkCall {
            val response = autservice.create(EntradasSalidasApiClient::class.java).getInputOutput(User.token, inputOutputRequest)
            evaluateResponce(response.codigo)
            response
        }
    suspend fun addInputOutput(addInputOutputRequestRequest: AddInputOutputRequest): ApiResponceStatus<GeneralResponse> =
        makeNetworkCall {
            val response = autservice.create(EntradasSalidasApiClient::class.java).addInputOutput(User.token, addInputOutputRequestRequest)
            evaluateResponce(response.codigo)
            response
        }
    suspend fun deleteInputOutput(cia:String, numEmp:String, fecha:String, sec:String, usuario:String): ApiResponceStatus<GeneralResponse> =
        makeNetworkCall {
            val response = autservice.create(EntradasSalidasApiClient::class.java).deleteInputOutput(User.token, cia, numEmp, fecha, sec, usuario)
            evaluateResponce(response.codigo)
            response
        }
    suspend fun editInputOutput(cia:String, numEmp:String, fecha:String, sec:String, editInputOutputRequest: EditInputOutputRequest): ApiResponceStatus<GeneralResponse> =
        makeNetworkCall {
            val response = autservice.create(EntradasSalidasApiClient::class.java).editInputOutput(User.token, cia, numEmp, fecha, sec, editInputOutputRequest)
            evaluateResponce(response.codigo)
            response
        }
}