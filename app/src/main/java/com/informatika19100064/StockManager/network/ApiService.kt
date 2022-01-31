package com.informatika19100064.StockManager.network

import com.informatika19100064.StockManager.model.ResponseActionBarang
import com.informatika19100064.StockManager.model.ResponseBarang
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("read.php")
    fun getBarang(): Call<ResponseBarang>

    @FormUrlEncoded
    @POST("create.php")
    fun insertBarang(
        @Field("nama_barang") namaBarang: String?,
        @Field("jumlah_barang") jmlBarang: String?,
        @Field("kode_barang") kodeBarang: String?,
        @Field("harga_barang") hargaBarang: String?
    ): Call<ResponseActionBarang>

    @FormUrlEncoded
    @POST("update.php")
    fun updateBarang(
        @Field("id") id: String?,
        @Field("nama_barang") namaBarang: String?,
        @Field("jumlah_barang") jmlBarang: String?,
        @Field("kode_barang") kodeBarang: String?,
        @Field("harga_barang") hargaBarang: String?
    ): Call<ResponseActionBarang>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteBarang(
        @Field("id") id: String?
    ): Call<ResponseActionBarang>


}