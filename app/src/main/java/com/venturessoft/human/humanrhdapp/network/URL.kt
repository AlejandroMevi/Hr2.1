package com.venturessoft.human.humanrhdapp.network

import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User.Companion.ambiente

class URL {
    companion object {
        var URL_BASE = "http://192.168.0.16/Human/RHD/"
        var URL_FREESTATION = "http://192.168.0.63/HumaneTime/api/"
        init {
            when (ambiente) {
                Constants.PREPRODUCTIVO -> {
                    URL_BASE = "http://192.168.0.16/Human/RHD/"
                    URL_FREESTATION = "http://192.168.0.16/HumaneTime/api/"
                }
                Constants.DESARROLLO -> {
                    URL_BASE = "http://192.168.0.16/Human/RHD/"
                    URL_FREESTATION = "http://192.168.0.16/HumaneTime/api/"
                }
                Constants.CONTROLCALIDAD -> {
                    URL_BASE = "http://192.168.0.16/Human/RHD/"
                    URL_FREESTATION = "http://192.168.0.16/HumaneTime/api/"
                }
            }
        }
    }
    class Login {
        companion object {
            const val LOGIN = "HumaneLand/LoginRHD"
            const val EMPLEADO_LISTADO = "HumaneLand/RHD/Listado/Page"
            const val OLVIDE_CONTRASENA = "LoginRHD/Remember/Password"
            const val DASHBOARD = "HumaneLand/RHD/DashboardEntradasSalidas"
            const val BUSQUEDA_EMPLEADO = "HumaneLand/RHD/Listado/Page"

        }
    }
    class ControlDeAusentismos {
        companion object {
            const val VACACIONES = "HumaneLand/Vacaciones"
            const val PROGANUALVACACIONES = "HumaneLand/PgmVacaciones"
            const val GETPERMISOS = "HumaneLand/RHD/getPermisos"
            const val GETLISTAAUSENTISMOS = "HumaneLand/RHD/getAusentismos"
            const val ADDPERMISOS = "HumaneLand/RHD/addPermisos"
            const val ADDAUSENTISMOS = "HumaneLand/RHD/Ausentismos"
            const val REPORTAUSENTISMOS = "HumaneLand/RHD/Ausentismos/Ausentismos"
            const val REPORTEVACACIONES = "HumaneLand/RHD/Ausentismos/Vacaciones"
            const val REPORTINCAPACIDADES = "HumaneLand/RHD/Ausentismos/Incapacidades"
            const val REPORTPERMISOS = "HumaneLand/RHD/Ausentismos/Permisos"
            const val AREA = "HumaneLand/RHD/Catalogo/AreaCentroLinea"
        }
    }
    class AdministracionDeTiempos {
        companion object {
            const val REPASISTENCIAORG = "HumaneLand/RHD/AsistenciaOrganigrama"
            const val REPCONTROLASISTENCIA = "HumaneLand/RHD/ControlAsistencia"
            const val REPENTRADASSALIDAS = "HumaneLand/RHD/EntradasSalidas"
            const val REPENTRADASSALIDASXCONCEPTO = "HumaneLand/RHD/EntradasSalidasPorConcepto"
            const val REPHORASLABORADAS = "HumaneLand/RHD/HoraLaboradas"
            const val REPTARJETAEMPLEADOS = "HumaneLand/RHD/TarjetaEmpleado"
            const val REPORTAUSENTISMOS = "HumaneLand/RHD/Ausentismos"
            const val REPDIASXDISF = "HumaneLand/RHD/DiasPorDisfrutar"
            const val REPSEGURIDADVIGI= "HumaneLand/RHD/SeguridadVigilancia"
            const val CATALOGOCONCEPTO = "HumaneLand/RHD/Catalogo/Concepto"
            const val CATALOGOCODID = "HumaneLand/RHD/Catalogo/CodId"
            const val CATALOGOPERMISOS = "HumaneLand/RHD/Catalogo/Permiso"
            const val CATALOGOGRUPOS = "HumaneLand/RHD/Catalogo/Grupo"
            const val CATALOGOPROCESO = "HumaneLand/RHD/Catalogo/Proceso"
            const val CATALOGSTATION= "HumaneLand/RHD/Catalogo/Estacion"
            const val CATALOGOROL = "HumaneLand/RHD/getCatalogoRolHorario"
            const val CATALOGOTURNO = "HumaneLand/RHD/getCatalogoTurno"
            const val MR = "HumaneLand/RHD/getMR"
            const val CATALOGOZONA = "HumaneLand/RHD/Catalogo/Zona"
            const val ADDINFORMACIONGENERAL = "HumaneLand/RHD/addAdministrarMR"
            const val GETSTATIONS = "HumaneLand/RHD/getEstaciones"
            const val ADDSTATIONS = "HumaneLand/RHD/addEstaciones"
            const val GETNEGOCIACIONHORARIO = "HumaneLand/RHD/getNegociacionHorario"
            const val POSTNEGOCIACIONHORARIO = "HumaneLand/RHD/addNegociacionHorario"
            const val PUTNEGOCIACIONHORARIO = "HumaneLand/RHD/updateNegociacionHorario"
            const val DELETENEGOCIACIONHORARIO = "HumaneLand/RHD/deleteNegociacionHorario"
            const val GETENTRADASSALIDAS = "HumaneLand/RHD/getEntradasSalidas"
            const val ADDENTRADASSALIDAS = "HumaneLand/RHD/addEntradasSalidas"
            const val PREAUTORIZACIONTIEMPOS = "HumaneLand/RHD/PreautorizacionTiempoExtra"
            const val ADDPREAUTORIZACIONTIEMPOS = "HumaneLand/RHD/addPreautorizacionTiempoExtra"
            const val EDITPREAUTORIZACIONTIEMPOS = "HumaneLand/RHD/updatePreautorizacionTiempoExtra"
            const val SUPERVISOR = "HumaneLand/RHD/Catalogo/Supervisor"
            const val MOTIVOS = "HumaneLand/RHD/Catalogo/Motivo"
            const val CALENDARIO = "HumaneLand/RHD/getCatalogoCalendario"
            const val DEPARTAMENT = "HumaneLand/RHD/Catalogo/Departamento"
            const val CATEGORY = "HumaneLand/RHD/Catalogo/Categoria"
        }
    }
    class Kardex {
        companion object {
            const val KARDEX = "consultaKardex/Consulta/Reporte"
            const val KARDEXANUAL = "consultaKardex/Consulta/Anual"
            const val DIASFESTIVOS = "consultaKardex/Consulta/DiasFestivos"
            const val DIASDESCANSOS = "consultaKardex/Consulta/DiasDescanso"
        }
    }
    class FreeStation {
        companion object {
            const val FREE = "AdministrarEstaciones/ReporteChecadas"
            const val FREEDETAILS = "AdministrarEstaciones/ReporteChecadas/Detalle"
            const val TOKEN = "Authorization/AccesoToken"
        }
    }
}