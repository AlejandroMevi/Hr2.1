package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.CalendarResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.CategoryResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.DepartamentResponce
import com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.CatalogResponse
import com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.net.TimeManagementApi
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class TimeManagementRep {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getRolTurn(): ApiResponceStatus<List<Any>> =
        withContext(Dispatchers.IO) {
            try {
                val deferreds = listOf(
                    async { autservice.create(TimeManagementApi::class.java).getCatalogoRol(User.token, User.numCia) },
                    async { autservice.create(TimeManagementApi::class.java).getCatalogoTurno(User.token, User.numCia) }
                )
                ApiResponceStatus.Success(deferreds.awaitAll())
            } catch (exception: java.lang.Exception) {
                ApiResponceStatus.Error(exception.message.toString())
            }
        }

    suspend fun getAreaCentroLinea(area: String?, centro: String?): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getAreaCentroLinea(User.token, User.numCia, User.usuario, area, centro)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun getConceptos(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getConceptos(User.token, User.numCia)
            evaluateResponce(response.codigo.toString())
            response
        }
    }

    suspend fun getZona(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getZona(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun getSupervisor(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getSupervisor(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun getReasons(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getMotivos(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun getCalendar(): ApiResponceStatus<CalendarResponce> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getCalendar(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun getDepartment(): ApiResponceStatus<DepartamentResponce> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getDepartment(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun getCategory(): ApiResponceStatus<CategoryResponce> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getCategory(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun codid(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).codid(User.token, User.numCia, User.usuario)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun catalogoPermisos(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).catalogoPermisos(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun station(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).station(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun catalogoGrupos(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getCatalogoGrupos(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun catalogoProceso(): ApiResponceStatus<CatalogResponse> {
        return makeNetworkCall {
            val response = autservice.create(TimeManagementApi::class.java).getCatalogoProceso(User.token, User.numCia)
            evaluateResponce(response.codigo)
            response
        }
    }
}