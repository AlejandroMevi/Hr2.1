package com.venturessoft.human.humanrhdapp.core

import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.TIMEOUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponceStatus<T> = withContext(Dispatchers.IO){
    try {
        ApiResponceStatus.Success(call())
    }catch (exception:Exception){
        var messageException = exception.message.toString()
        if (messageException.lowercase().contains("timedout") ||
            messageException.lowercase().contains("timed out") ||
            messageException.lowercase().contains("timed_out") ||
            messageException.lowercase().contains("timeout")||
            messageException.lowercase().contains("time out")||
            messageException.lowercase().contains("time_out")||
            messageException.lowercase().contains("failed to connect to") ||
            messageException.lowercase().contains("unable to resolve host") ||
            messageException.lowercase().contains("timed out waiting for")){
            messageException = TIMEOUT
        }
        ApiResponceStatus.Error(messageException)
    }
}
fun evaluateResponce(codigo: String, errorMessage: String? = null) {
    if (codigo != "ET000" && codigo != "OK" && codigo != "0" && codigo != "RHD000" &&  codigo != "Exito") {
        if (codigo.isNotEmpty()){
            throw Exception(codigo)
        }else{
            if (!errorMessage.isNullOrEmpty()){
                throw Exception(errorMessage)
            }else{
                throw Exception("")
            }
        }
    }
}