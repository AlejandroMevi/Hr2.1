package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.ReportResponse
import com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReportVacacionesCaApiClient {

    @Headers(
        "Content-Type: application/json;charset=UTF-8"
    )
    @GET(URL.ControlDeAusentismos.REPORTEVACACIONES)
    suspend fun getReportVacacionesCa(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("todosLosAnios") todosLosAnios: Boolean? = null,
        @Query("programacion") programacion: Boolean? = null,
        @Query("salidas") salidas: Boolean? = null,
    ): ReportResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.ControlDeAusentismos.REPORTAUSENTISMOS)
    suspend fun getReportAusentismos(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null
        ): ReportResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8"
    )
    @GET(URL.ControlDeAusentismos.REPORTINCAPACIDADES)
    suspend fun getReportIncapacidadesCa(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null
        ): ReportResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8"
    )
    @GET(URL.ControlDeAusentismos.REPORTPERMISOS)
    suspend fun getReportPermisosCa(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null
        ): ReportResponse
}