package com.venturessoft.human.humanrhdapp.core
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
class RetrofitDataSource(tipo: TypeServices) {
    private val retrofit: Retrofit
    init {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val uRLTypeServices: String = when (tipo) {
            TypeServices.BASE -> {
                URL.URL_BASE
            }
            TypeServices.FREESTATION -> {
                URL.URL_FREESTATION
            }
        }
        val builder = Retrofit.Builder()
            .baseUrl(uRLTypeServices)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS).build()
        retrofit = builder.client(okHttpClient).build()
    }
    fun getRetrofit(): Retrofit {
        return retrofit
    }
}