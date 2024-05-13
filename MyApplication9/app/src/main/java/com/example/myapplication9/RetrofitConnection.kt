package com.example.myapplication9

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=100&returnType=json&serviceKey=APKTrp0XMZTlReSionHVfAbVsgefp6rmsviSNGmE5MndTP43LqhqvSm2n7Qj%2B2GQ3TpsgbH%2FKaUWDEMV5ApISg%3D%3D

class RetrofitConnection {

    // 전역 변수로 선언
    companion object {
        private const val BASE_URL = "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/"
        var jsonNetServ: NetworkService
        val jsonREtrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // xml 통신을 위한 변수
        val xmlNetworkServ: NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init {
            jsonNetServ = jsonREtrofit.create(NetworkService::class.java)
            xmlNetworkServ = xmlRetrofit.create(NetworkService::class.java)
        }

    }


}