package com.informatika19100064.StockManager

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.informatika19100064.StockManager.adapter.ListContent
import com.informatika19100064.StockManager.model.ResponseActionBarang
import com.informatika19100064.StockManager.model.ResponseBarang
import com.informatika19100064.StockManager.network.koneksi
import kotlinx.android.synthetic.main.activity_insert_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertDataActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        toolbar.title = "Masukan Data"
        toolbar.setTitleTextColor(Color.WHITE)

        btn_submit.setOnClickListener {
            val etNamaBarang = et_nama_barang.text
            val etJmlBarang = et_jumlah_barang.text
            val etkodeBarang = et_kode_barang.text
            val ethargaBarang = et_harga_barang.text
            if (etJmlBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Jumlah Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (etNamaBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Nama Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (etkodeBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Kode Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (ethargaBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Harga Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                actionData(etNamaBarang.toString(), etJmlBarang.toString(), etkodeBarang.toString(), ethargaBarang.toString())
            }
        }

        btn_clean.setOnClickListener {
            formClear()
        }
        getData()
    }

    fun formClear() {
        et_nama_barang.text.clear()
        et_jumlah_barang.text.clear()
        et_kode_barang.text.clear()
        et_harga_barang.text.clear()

    }

    fun actionData(namaBarang: String, jmlBarang: String, kodeBarang: String, hargaBarang: String) {
        koneksi.service.insertBarang(namaBarang, jmlBarang, kodeBarang, hargaBarang)
            .enqueue(object : Callback<ResponseActionBarang> {
                override fun onFailure(call: Call<ResponseActionBarang>, t: Throwable) {
                    Log.d("pesan1", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseActionBarang>,
                    response: Response<ResponseActionBarang>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@InsertDataActivity,
                            "data berhasil disimpan",
                            Toast.LENGTH_LONG
                        ).show()
                        formClear()
                        getData()
                    }
                }
            })
    }

    fun getData() {
        koneksi.service.getBarang().enqueue(object : Callback<ResponseBarang> {
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if (response.isSuccessful) {
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@InsertDataActivity, "InsertDataActivity")

                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@InsertDataActivity)
                    }
                }
            }
        })
    }
}

