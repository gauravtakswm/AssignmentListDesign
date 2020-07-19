package com.assignment.listdesign.di.module


import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @get:GET("s/2iodh4vg0eortkl/facts.json")
    val listData: Call<ListDataResponse?>?
}