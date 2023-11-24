package com.venturessoft.human.humanrhdapp.jwt

import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import androidx.annotation.Nullable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.venturessoft.human.humanrhdapp.utilis.DecodeException
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.util.Date
import kotlin.math.floor
class JWTUtils(private val tokenJWT: String): Parcelable {
    private var header: Map<String, String>? = null
    private var payload: JWTPayload? = null
    private var signature: String? = null
    init {
        decode(tokenJWT)
    }
    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
        signature = parcel.readString()
    }
    fun isExpired(leeway: Long): Boolean {
        require(leeway >= 0) { "The leeway must be a positive value." }
        val todayTime = (floor((Date().time / 1000).toDouble()) * 1000).toLong()
        val futureToday = Date(todayTime + leeway * 1000)
        val pastToday = Date(todayTime - leeway * 1000)
        val expValid = payload!!.exp == null || !pastToday.after(payload!!.exp)
        val iatValid = payload!!.iat == null || !futureToday.before(payload!!.iat)
        return !expValid || !iatValid
    }
    private fun decode(token: String) {
        val isBearer = token.replace("Bearer ", "")
        val partsJWT = splitToken(isBearer)
        val mapType: Type = object : TypeToken<Map<String?, String?>?>() {}.type
        header = parseJson( base64Decode(partsJWT[0].toString()), mapType)
        payload = parseJson(base64Decode(partsJWT[1].toString()), JWTPayload::class.java)
        signature = partsJWT[2]
    }
    private fun splitToken(token: String): Array<String?> {
        var parts: Array<String?> = token.split("\\.".toRegex()).toTypedArray()
        if (parts.size == 2 && token.endsWith(".")) {
            parts = arrayOf(parts[0], parts[1], "")
        }
        if (parts.size != 3) {
            throw DecodeException(
                String.format("The token was expected to have 3 parts, but got %s.", parts.size)
            )
        }
        return parts
    }
    @Nullable
    private fun base64Decode(string: String): String {
        val decoded: String = try {
            val bytes: ByteArray = Base64.decode(string, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
            String(bytes, Charset.defaultCharset())
        } catch (e: IllegalArgumentException) {
            throw DecodeException("Received bytes didn't correspond to a valid Base64 encoded string.", e)
        }
        return decoded
    }
    private fun <T> parseJson(json: String, typeOfT: Type): T {
        val payload: T = try {
            getGson().fromJson(json, typeOfT)
        } catch (e: Exception) {
            throw DecodeException("The token's payload had an invalid JSON format.", e)
        }
        return payload
    }
    private fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(JWTPayload::class.java, JWTDeserializer())
            .create()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tokenJWT)
        parcel.writeString(signature)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<JWTUtils> {
        override fun createFromParcel(parcel: Parcel): JWTUtils {
            return JWTUtils(parcel)
        }
        override fun newArray(size: Int): Array<JWTUtils?> {
            return arrayOfNulls(size)
        }
    }
}