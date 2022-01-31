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
import kotlinx.android.synthetic.main.activity_update_data.*
import kotlinx.android.synthetic.main.activity_update_data.et_jumlah_barang
import kotlinx.android.synthetic.main.activity_update_data.et_nama_barang
import kotlinx.android.synthetic.main.activity_update_data.rv_data_barang
import kotlinx.android.synthetic.main.activity_update_data.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDataActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        toolbar.title = "Ubah Data"
        toolbar.setTitleTextColor(Color.WHITE)

        val i = intent
        val idBarang = i.getStringExtra("IDBARANG")
        val namaBarang = i.getStringExtra("NAMABARANG")
        val jumlahBarang = i.getStringExtra("JUMLAHBARANG")
        val kodeBarang = i.getStringExtra("KODEBARANG")
        val hargaBarang = i.getStringExtra("HARGABARANG")

        et_nama_barang.setText(namaBarang)
        et_jumlah_barang.setText(jumlahBarang)
        et_kode_barang.setText(kodeBarang)
        et_harga_barang.setText(hargaBarang)
        btn_submit.setOnClickListener {
            val etNamaBarang = et_nama_barang.text
            val etJmlBarang = et_jumlah_barang.text
            val etkodeBarang = et_kode_barang.text
            val ethargaBarang = et_harga_barang.text
            if (etJmlBarang.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Jumlah Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }else if (etNamaBarang.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Nama Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            } else if (etkodeBarang.isEmpty()) {
                Toast.makeText(
                    this@UpdateDataActivity,
                    "Kode Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (ethargaBarang.isEmpty()) {
                Toast.makeText(
                    this@UpdateDataActivity,
                    "Harga Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                actionData(idBarang.toString(), etNamaBarang.toString(), etJmlBarang.toString(), etkodeBarang.toString(), ethargaBarang.toString())
            }
        }
        btn_back.setOnClickListener {
            finish()
        }
        getData()
    }
    fun actionData(id : String, namaBarang : String, jmlBarang : String, kodeBarang: String, hargaBarang: String){
        koneksi.service.updateBarang(id, namaBarang, jmlBarang, kodeBarang, hargaBarang).enqueue(object : Callback<ResponseActionBarang>{
            override fun onFailure(call: Call<ResponseActionBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseActionBarang>,
                response: Response<ResponseActionBarang>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(this@UpdateDataActivity, "data berhasil diupdate", Toast.LENGTH_LONG).show()
                    getData()
                }
            }
        })
    }
    fun getData(){
        koneksi.service.getBarang().enqueue(object : Callback<ResponseBarang>{
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if (response.isSuccessful){
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@UpdateDataActivity,"UpdateDataActivity")


                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@UpdateDataActivity)
                    }

                    }
                }

        })
    }
}