package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.MarcaItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.*
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net.GetDiasDescansosApiClient
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net.GetDiasFestivosApiClient
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import kotlinx.coroutines.*
class KardexAnualService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun kardexAnual(token: String, kardexAnualRequest: KardexAnualRequest): ApiResponceStatus<KardexAnualResponse> {
        return makeNetworkCall {
            val response = autservice.create(KardexAnualApiClient::class.java)
                .getKardexAnual(token, kardexAnualRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getKardexAnual(token: String, kardexAnualRequest: List<KardexAnualRequest>): KardexAnualModel {
        return withContext(Dispatchers.IO) {
            val listData = mutableListOf<List<MarcaItem>>()
            val listholydays = mutableListOf<List<DiasFestivosItem>>()
            val listDaysoff = mutableListOf<List<DiasDescansoItem>>()
            try {
                val deferreds = mutableListOf<Deferred<KardexAnualResponse>>()
                kardexAnualRequest.forEach {kardex->
                    deferreds.add( async {
                        autservice.create(KardexAnualApiClient::class.java).getKardexAnual(token, kardex)
                    })
                }
                deferreds.awaitAll()

                deferreds.forEach {
                    if (it.getCompleted().skardexAnual != null){
                        listData.add(it.getCompleted().skardexAnual.marca)
                    }else{
                        listData.add(listOf())
                    }
                }
                val deferredsHolydays = mutableListOf<Deferred<DiasFestivosResponse>>()
                kardexAnualRequest.forEach {kardex->
                    deferredsHolydays.add( async {
                        autservice.create(GetDiasFestivosApiClient::class.java).getDiasFestivos(token, DiasFestivosRequest(cia = kardex.cia, anio = kardex.anio))
                    })
                }
                deferredsHolydays.awaitAll()
                deferredsHolydays.forEach {
                    if (it.getCompleted().diasFestivos != null){
                        listholydays.add(it.getCompleted().diasFestivos)
                    }else{
                        listholydays.add(listOf())
                    }
                }
                val deferredsDaysOff = mutableListOf<Deferred<DiasDescansosResponse>>()
                kardexAnualRequest.forEach {kardex->
                    deferredsDaysOff.add( async {
                        autservice.create(GetDiasDescansosApiClient::class.java).getDiasDescansos(token, DiasDescansosRequest(cia = kardex.cia, anio = kardex.anio, numEmp = kardex.numEmp))
                    })
                }
                deferredsDaysOff.awaitAll()
                deferredsDaysOff.forEach {
                    if (it.getCompleted().diasDescanso != null){
                        listDaysoff.add(it.getCompleted().diasDescanso)
                    }else{
                        listDaysoff.add(listOf())
                    }
                }
            }catch (_:Exception){
                KardexAnualModel(listData,listholydays,listDaysoff)
            }
            KardexAnualModel(listData,listholydays,listDaysoff)
        }
    }
}