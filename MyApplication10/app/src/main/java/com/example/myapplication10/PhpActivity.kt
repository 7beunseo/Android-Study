package com.example.myapplication10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication10.databinding.ActivityPhpBinding
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPhpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhp.setOnClickListener {
            val call: Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList()

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PhpResponse>,
                    response: Response<PhpResponse>
                ) {
                    Log.d("mobileApp", "$response")
                    Log.d("mobileApp", "${response.body()}")
                    binding.phpRecyclerView.adapter = PhpAdapter(this@PhpActivity, response.body()?.result!!) // null이면 안됨
                    binding.phpRecyclerView.layoutManager = LinearLayoutManager(this@PhpActivity)
                    binding.phpRecyclerView.addItemDecoration(DividerItemDecoration(this@PhpActivity, LinearLayoutManager.VERTICAL))
                }

                override fun onFailure(call: retrofit2.Call<PhpResponse>, t: Throwable) {
                    Log.d("mobileApp", "onFailure")
                }


            })
        }
    }
}