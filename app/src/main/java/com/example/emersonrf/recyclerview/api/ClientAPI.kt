package com.example.emersonrf.recyclerview.api

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.AccessControlContext
import java.util.concurrent.TimeUnit


// codigo responsavel para realizar as chamadas API




class ClientApi<T> {

    fun getClient(c: Class<T>): T {
        val retrofit = Retrofit.Builder()
                .client(getOkhttpClientAuth().build()) //chamando o metodo de cliente autenticado
                .baseUrl("https://pokedexdx.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(c)
    }
}


private var picasso: Picasso? =null

fun getPicassoauth(context: Context): Picasso {

    if(picasso == null){

        picasso = Picasso
                .Builder(context)
                .downloader(OkHttp3Downloader(getOkhttpClientAuth().build()))
                .build()
    }
    return picasso!!
}

fun getOkhttpClientAuth() : OkHttpClient.Builder {

    return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor()) //aqui esta chamado a classe AuthInterceptor
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val requestBuilder = chain!!.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Basic cG9rZWFwaTpwb2tlbW9u") //cabecalho para implantar o token
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code() == 401) {
            Log.e("MEUAPP", "Error API KEY")
        }
        return response
    }
}


fun getPokemonAPI(): PokemonAPI {

    return ClientApi<PokemonAPI>().getClient(PokemonAPI::class.java)

}