package com.gauravtak.assignment_list_design.views.presentation.list_screen

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.assignment.listdesign.di.module.ApiInterface
import com.gauravtak.assignment_list_design.AssignmentListDesignApp
import com.gauravtak.assignment_list_design.R
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.LiveDataEvent
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper
import com.gauravtak.assignment_list_design.views.custom_views.CustomProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

//the ListActivityViewModel is associated with ListActivity to perform api call and other logical operations
 class ListActivityViewModel :ViewModel()
{

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var application: Application

    private lateinit var listActivity:ListActivity

    val listResponseDataEvent:LiveDataEvent<List<ListDataResponse.RowsBean?>> =  LiveDataEvent()
    val toolbarTitle: ObservableField<String> by lazy{ ObservableField<String>() }
    val isLoading: ObservableBoolean by lazy { ObservableBoolean() }
    val isNoDataVisible: ObservableBoolean by lazy { ObservableBoolean()}


    fun init(listActivity: ListActivity) {
        this.listActivity = listActivity;
        //initialized the dagger2 injection for getting application and retrofit objects
        (listActivity.getApplication() as AssignmentListDesignApp).component.inject(this)

    }
    fun onRefresh()
    {
        //Swipe refresh functionality added , method calling is given inside the layout xml file
        isLoading.set(true)
        android.os.Handler().postDelayed({
            isLoading.set(false)
          getListDataApiCall()
        }, 500L)


    }
     fun getListDataApiCall()
    {
        //showing custom dialog for api progress
        CustomProgressDialog.showProgress(listActivity)
        val apiService: ApiInterface = retrofit.create(ApiInterface::class.java)

        val call = apiService.listData
        call!!.enqueue(object : Callback<ListDataResponse?> {
            override fun onResponse(call: Call<ListDataResponse?>, response: Response<ListDataResponse?>) {
              CustomProgressDialog.hideprogressbar()
                if(response.body()!=null) {
                        val listDataResponse = response.body()
                    toolbarTitle.set(listDataResponse?.getTitle()) //Updating the title of toolbar based on api response
                    if(listDataResponse?.getRows()!=null && listDataResponse.getRows()!!.size>0) {
                        isNoDataVisible.set(false);// no data is hidden because recycler view data is received as api response
                        listResponseDataEvent.value = listDataResponse.getRows() // updating the data of recycler View based on api response
                    }else {
                        isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                    }

                }else {
                    isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                    UtilHelper.showSnackBar(listActivity, listActivity.getString(R.string.no_response_received))
                }
            }

            override fun onFailure(call: Call<ListDataResponse?>, t: Throwable) {
                CustomProgressDialog.hideprogressbar()
                isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                UtilHelper.showSnackBar(listActivity,listActivity.getString(R.string.error_in_api_response))

            }
        })

    }
}