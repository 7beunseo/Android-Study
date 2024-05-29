package com.example.myapplication10

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=10&returnType=xml&serviceKey=서비스키(일반 인증키(Encoding))
// http://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3?serviceKey=APKTrp0XMZTlReSionHVfAbVsgefp6rmsviSNGmE5MndTP43LqhqvSm2n7Qj%2B2GQ3TpsgbH%2FKaUWDEMV5ApISg%3D%3D
class RetrofitConnection{

    //객체를 하나만 생성하는 싱글턴 패턴을 적용합니다.
    companion object {
        //API 서버의 주소가 BASE_URL이 됩니다.
        private const val BASE_URL = "http://apis.data.go.kr/B553748/CertImgListServiceV3/"

        var xmlNetworkService : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        // http://localhost/php_connection.php/

        private const val BASE_URL_PHP = "http://172.20.4.10/" // ipconfig
        var phpNetworkService: NetworkService
        val phpRetrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_PHP)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init{
            xmlNetworkService = xmlRetrofit.create(NetworkService::class.java)
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
        }


    }



}