package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.net

import android.content.Context
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.ReportRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.ReportResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class ReportVacacionesCaService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun reportVacacionesCa(
        reportRequest: ReportRequest,
        context: Context
    ): ApiResponceStatus<ReportResponse> {
        return makeNetworkCall {
            val response = when (reportRequest.typeReport) {
                context.getString(R.string.menu_vacaciones) -> autservice.create(
                    ReportVacacionesCaApiClient::class.java
                ).getReportVacacionesCa(
                    reportRequest.token.toString(),
                    reportRequest.numEmp,
                    reportRequest.cia,
                    reportRequest.fechaTermino,
                    reportRequest.fechaInicio,
                    reportRequest.usuario,
                    reportRequest.area,
                    reportRequest.centro,
                    reportRequest.linea,
                    reportRequest.todosLosAnios,
                    reportRequest.programacion,
                    reportRequest.salidas
                )

                context.getString(R.string.menu_incapacidades) -> autservice.create(
                    ReportVacacionesCaApiClient::class.java
                ).getReportIncapacidadesCa(
                    reportRequest.token.toString(),
                    reportRequest.numEmp,
                    reportRequest.cia,
                    reportRequest.fechaTermino,
                    reportRequest.fechaInicio,
                    reportRequest.usuario,
                    reportRequest.area,
                    reportRequest.centro,
                    reportRequest.linea,
                    reportRequest.supervisor
                )

                context.getString(R.string.menu_ausentismos) -> autservice.create(
                    ReportVacacionesCaApiClient::class.java
                ).getReportAusentismos(
                    reportRequest.token.toString(),
                    reportRequest.numEmp,
                    reportRequest.cia,
                    reportRequest.fechaTermino,
                    reportRequest.fechaInicio,
                    reportRequest.usuario,
                    reportRequest.area,
                    reportRequest.centro,
                    reportRequest.linea,
                    reportRequest.supervisor
                )

                context.getString(R.string.menu_permisos) -> autservice.create(
                    ReportVacacionesCaApiClient::class.java
                ).getReportPermisosCa(
                    reportRequest.token.toString(),
                    reportRequest.numEmp,
                    reportRequest.cia,
                    reportRequest.fechaTermino,
                    reportRequest.fechaInicio,
                    reportRequest.usuario,
                    reportRequest.area,
                    reportRequest.centro,
                    reportRequest.linea,
                    reportRequest.supervisor
                )

                else -> ReportResponse("", "", "","")
            }
            evaluateResponce(response.codigo)
            response
        }
    }
}