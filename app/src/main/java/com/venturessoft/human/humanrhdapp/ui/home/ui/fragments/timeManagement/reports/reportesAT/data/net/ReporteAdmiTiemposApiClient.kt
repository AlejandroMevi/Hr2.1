package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.data.models.ReporteAdmiTiemposResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReporteAdmiTiemposApiClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPASISTENCIAORG)
    suspend fun getReportAsistOrgAt(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("zona") zona: Long? = null,
        @Query("turno") turno: Int? = null,
        @Query("rol") rol: Int? = null,
        @Query("general") general: Boolean? = null,
        @Query("detalle") detalle: Boolean? = null,
        ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPORTAUSENTISMOS)
    suspend fun reporteAusentismosAT(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("retardos") retardos: Boolean? = null,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null
        ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPCONTROLASISTENCIA)
    suspend fun reporteControlAsistenciaAT(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("grupo") grupo: String? = null,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null
    ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPENTRADASSALIDASXCONCEPTO)
    suspend fun reportEentradasSalidasXConceptoAT(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null,
        @Query("descripcionPorEmpleado") descripcionPorEmpleado: Boolean? = null,
        @Query("totalDepartamento") totalDepartamento: Boolean? = null,
        @Query("conceptos") conceptos: ArrayList<Int>? = null
    ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPENTRADASSALIDAS)
    suspend fun reportEentradasSalidasAT(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null,
        @Query("impresionPorEmpleado") impresionPorEmpleado: Boolean? = null,
        @Query("empleadosInactivo") empleadosInactivo: Boolean? = null,
        @Query("mrActiva") mrActiva: Boolean? = null,
    ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPHORASLABORADAS)
    suspend fun reporteHorasLaboradas(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("cuentaContable") cuentaContable: Boolean? = null,
        @Query("supervisor") supervisor: String? = null
        ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPTARJETAEMPLEADOS)
    suspend fun reporteTarjetaEmpleadosAT(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null,
        ): ReporteAdmiTiemposResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPDIASXDISF)
    suspend fun reporteDiasXDisfrutar(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("cia") cia: Long,
        @Query("anio") anio: Int? = null,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("codId") codId: Long? = null
    ): ReporteAdmiTiemposResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.REPSEGURIDADVIGI)
    suspend fun reporteSeguridadVigilancia(
        @Header("Authorization") token: String,
        @Query("numEmp", encoded = true) numEmp: List<Long>? = null,
        @Query("numCia") numCia: Long,
        @Query("fechaTermino") fechaTermino: String,
        @Query("fechaInicio") fechaInicio: String,
        @Query("usuario") usuario: String,
        @Query("area") area: String? = null,
        @Query("centro") centro: String? = null,
        @Query("linea") linea: String? = null,
        @Query("supervisor") supervisor: String? = null,
        @Query("negociacionHorario") negociacionHorario: Boolean? = null,
        @Query("registroTiempoExtra") registroTiempoExtra: Boolean? = null
    ): ReporteAdmiTiemposResponse

}