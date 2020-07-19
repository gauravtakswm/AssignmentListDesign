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
import com.gauravtak.assignment_list_design.db_storage.DatabaseHandler
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.AppConstants
import com.gauravtak.assignment_list_design.utils_classes.LiveDataEvent
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper
import com.gauravtak.assignment_list_design.views.custom_views.CustomProgressDialog
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

//the ListActivityViewModel is associated with ListActivity to perform api call and other logical operations
 class ListActivityViewModel :ViewModel()
{

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var application: Application

    private lateinit var listActivity:ListActivity
    private lateinit var listDataResponse: ListDataResponse

    val listResponseDataEvent:LiveDataEvent<ListDataResponse> =  LiveDataEvent()
    val toolbarTitle: ObservableField<String> by lazy{ ObservableField<String>() }
    val isLoading: ObservableBoolean by lazy { ObservableBoolean() }
    val isNoDataVisible: ObservableBoolean by lazy { ObservableBoolean()}
    private  var isValueFromDb:Boolean = false

    fun init(listActivity: ListActivity) {
        this.listActivity = listActivity;
        //initialized the dagger2 injection for getting application and retrofit objects
        (listActivity.getApplication() as AssignmentListDesignApp).component.inject(this)
        getDataFromStorage()
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
    //get the data from internal sqlite storage
    private fun getDataFromStorage() {
        val dataValue = DatabaseHandler(AssignmentListDesignApp.mContext).getScreenData(AppConstants.SCREEN_KEY_LIST_DATA);
        if(dataValue!=null) {
            isValueFromDb = true; // is data available
            listDataResponse = Gson().fromJson(dataValue,ListDataResponse::class.java)

            try {
                toolbarTitle.set(listDataResponse.getTitle())
                listResponseDataEvent.value = listDataResponse // updating the data of recycler View based on api response
            }catch (e: Exception){}
        }else
        {
            isValueFromDb = false; // is data not available

        }

    }
     fun getListDataApiCall()
    {
        //showing custom dialog for api progress
        if(!isValueFromDb)//this progress dialog will be shown when no data is present into internal storage
            CustomProgressDialog.showProgress(listActivity) //first time show the progress dialog of api
        val apiService: ApiInterface = retrofit.create(ApiInterface::class.java)

        val call = apiService.listData
        call!!.enqueue(object : Callback<ListDataResponse?> {
            override fun onResponse(call: Call<ListDataResponse?>, response: Response<ListDataResponse?>) {
                if(!isValueFromDb)
                CustomProgressDialog.hideprogressbar() // hide the progress dialog
                if(response.body()!=null) {
                        val listDataResponse = response.body()
                    toolbarTitle.set(listDataResponse?.getTitle()) //Updating the title of toolbar based on api response
                    if(listDataResponse?.getRows()!=null && listDataResponse.getRows()!!.size>0) {
                        isNoDataVisible.set(false);// no data is hidden because recycler view data is received as api response
                        listResponseDataEvent.value = listDataResponse // updating the data of recycler View based on api response
                    saveDataIntoStorage(listDataResponse) // add the data or update the data if existing
                    }else {
                        isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                    }

                }else {
                    isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                    UtilHelper.showSnackBar(listActivity, listActivity.getString(R.string.no_response_received))
                }
            }

            override fun onFailure(call: Call<ListDataResponse?>, t: Throwable) {
                if(!isValueFromDb)
                    CustomProgressDialog.hideprogressbar() // hide the progress dialog   isNoDataVisible.set(true) //no data text is shown if recycler view data is not received in api response
                UtilHelper.showSnackBar(listActivity,listActivity.getString(R.string.error_in_api_response))

            }
        })

    }
    //saving new api data into sqlite storage as per the need
    fun saveDataIntoStorage(listDataResponse: ListDataResponse)
    {
        val stringValue = Gson().toJson(listDataResponse)
        if(!isValueFromDb)
            DatabaseHandler(AssignmentListDesignApp.mContext).addScreenData(stringValue, AppConstants.SCREEN_KEY_LIST_DATA);
        else
            DatabaseHandler(AssignmentListDesignApp.mContext).updateScreenData(stringValue, AppConstants.SCREEN_KEY_LIST_DATA);

    }
}