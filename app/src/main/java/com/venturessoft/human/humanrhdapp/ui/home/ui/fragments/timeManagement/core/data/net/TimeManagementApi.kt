package com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.*
import com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.CatalogResponse
import com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models.GetCatalogoRolResponse
import com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models.GetCatalogoTurnoResponse
import retrofit2.http.*

interface TimeManagementApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOROL)
    suspend fun getCatalogoRol(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long
    ): GetCatalogoRolResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOTURNO)
    suspend fun getCatalogoTurno(
        @Header("Authorization") token: String,
        @Query("cia") cia: Long
    ): GetCatalogoTurnoResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.ControlDeAusentismos.AREA)
    suspend fun getAreaCentroLinea(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long,
        @Query("user") user: String,
        @Query("area") area: String?,
        @Query("centro") centro: String?
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOCONCEPTO)
    suspend fun getConceptos(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOZONA)
    suspend fun getZona(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.SUPERVISOR)
    suspend fun getSupervisor(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.MOTIVOS)
    suspend fun getMotivos(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CALENDARIO)
    suspend fun getCalendar(
        @Header("Authorization") token: String,
        @Query("cia") numCia: Long
    ): CalendarResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.DEPARTAMENT)
    suspend fun getDepartment(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): DepartamentResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATEGORY)
    suspend fun getCategory(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CategoryResponce
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOCODID)
    suspend fun codid(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long,
        @Query("userId") userId: String
    ): CatalogResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOPERMISOS)
    suspend fun catalogoPermisos(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGSTATION)
    suspend fun station(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOGRUPOS)
    suspend fun getCatalogoGrupos(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.AdministracionDeTiempos.CATALOGOPROCESO)
    suspend fun getCatalogoProceso(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long
    ): CatalogResponse
}

