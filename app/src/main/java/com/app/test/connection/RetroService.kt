package com.app.test.connection

import com.app.test.model.Evolution
import com.app.test.model.RecyclerList
import com.app.test.model.detailRecylerlist
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<RecyclerList>

    @GET("pokemon/{name}")
    fun getDetailPokemon(
        @Path("name") name: String
    ): Observable<detailRecylerlist>

    @GET("evolution-chain/{id}")
    fun getEvolutionPokemon(
        @Path("id") id: Int
    ): Observable<Evolution>


}