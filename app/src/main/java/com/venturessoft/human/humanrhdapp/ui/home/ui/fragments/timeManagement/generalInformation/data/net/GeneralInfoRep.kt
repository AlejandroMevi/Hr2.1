package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.AddStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.EditStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.InfoGenralRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.MRResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.StationResponce
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class GeneralInfoRep {
    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun getMR(numEmp: Long): ApiResponceStatus<MRResponce> {
        return makeNetworkCall {
            val response =
                autservice.create(GeneralInfoApi::class.java).getMR(User.token, User.numCia, numEmp)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun addAdministrarMR(infoGenralRequest: InfoGenralRequest): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(GeneralInfoApi::class.java)
                .addAdministrarMR(User.token, infoGenralRequest)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun editAdministrarMR(
        numEmp: Long,
        fechaAplicacion: String,
        infoGenralRequest: InfoGenralRequest
    ): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(GeneralInfoApi::class.java).editAdministrarMR(User.token, User.numCia, numEmp, fechaAplicacion, User.usuario, infoGenralRequest)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun getStation(numEmp: Long, gafete: Long): ApiResponceStatus<StationResponce> {
        return makeNetworkCall {
            val response = autservice.create(GeneralInfoApi::class.java)
                .getStations(User.token, User.numCia, numEmp, gafete)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun addStations(addStationRequest: AddStationRequest): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(GeneralInfoApi::class.java).addStations(User.token, addStationRequest)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun editStations(numEmp:Long,gafete:Long,editStationRequest: List<EditStationRequest>): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(GeneralInfoApi::class.java).editStations(User.token,User.numCia, numEmp, gafete,User.usuario,editStationRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}