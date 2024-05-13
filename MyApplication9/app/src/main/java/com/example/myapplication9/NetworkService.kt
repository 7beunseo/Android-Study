package com.example.myapplication9

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=100&returnType=json&serviceKey=APKTrp0XMZTlReSionHVfAbVsgefp6rmsviSNGmE5MndTP43LqhqvSm2n7Qj%2B2GQ3TpsgbH%2FKaUWDEMV5ApISg%3D%3D
interface NetworkService {
    // 기본 주소 이후의 부분을 만들어줌
    @GET("getUlfptcaAlarmInfo")

    // 함수 선언
    fun getJsonList(
        // url 들어가는 키
        @Query("year") year: Int,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("returnType") returnType: String,
        @Query("serviceKey") serviceKey: String
    ): Call<JsonResponse>

    @GET("getUlfptcaAlarmInfo")
    fun getXmlList(
        @Query("year") year: Int,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("returnType") returnType: String,
        @Query("serviceKey") serviceKey: String
    ): Call<XmlResponse>
}