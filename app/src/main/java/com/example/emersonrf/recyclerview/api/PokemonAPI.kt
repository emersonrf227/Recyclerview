package com.example.emersonrf.recyclerview.api

import android.database.Observable
import com.example.emersonrf.recyclerview.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PokemonAPI{


    @GET("/api/pokemon")

fun buscar(@Query("size") size: Int ): io.reactivex.Observable<PokemonResponse>


}