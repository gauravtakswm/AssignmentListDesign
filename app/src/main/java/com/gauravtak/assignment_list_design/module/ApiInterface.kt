package com.assignment.listdesign.di.module


import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import retrofit2.Call
import retrofit2.http.GET

//this interface would contain the every api call signature
interface ApiInterface {
    @get:GET("s/2iodh4vg0eortkl/facts.json")
    val listData: Call<ListDataResponse?>?
}