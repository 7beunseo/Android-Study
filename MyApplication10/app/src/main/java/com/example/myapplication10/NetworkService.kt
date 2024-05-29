package com.example.myapplication10


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=10&returnType=xml&serviceKey=서비스키(일반 인증키(Encoding))
// http://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3?serviceKey=APKTrp0XMZTlReSionHVfAbVsgefp6rmsviSNGmE5MndTP43LqhqvSm2n7Qj%2B2GQ3TpsgbH%2FKaUWDEMV5ApISg%3D%3D
interface NetworkService {
    @GET("getCertImgListServiceV3")
    fun getXmlList(
        @Query("prdlstNm") prdlstNm:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") apiKey:String
    ) : Call<XmlResponse>

    // http://localhost/php_connection.php/
    @GET("php_connection")
    fun getPhpList() : Call<PhpResponse>
}