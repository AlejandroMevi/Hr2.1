package com.venturessoft.human.humanrhdapp.ui.login.data.models

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("codigo")
    var codigo : String? = "",
    @SerializedName("resultado")
    var resultado : String? = ""
)
