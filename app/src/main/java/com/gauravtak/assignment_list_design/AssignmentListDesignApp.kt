package com.gauravtak.assignment_list_design

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.assignment.listdesign.di.module.ApiClientModule
import com.assignment.listdesign.di.module.ApiComponent
import com.assignment.listdesign.di.module.AppModule
import com.assignment.listdesign.di.module.DaggerApiComponent
import com.gauravtak.assignment_list_design.utils_classes.AppConstants


public class AssignmentListDesignApp: MultiDexApplication(){
    lateinit var component: ApiComponent

    override fun onCreate() {
        super.onCreate()
        mContext = this
        MultiDex.install(this)
        initDaggerComponent();
    }

    private fun initDaggerComponent() {
        component = DaggerApiComponent.builder()
                .appModule(AppModule(this))
                .apiClientModule(ApiClientModule(AppConstants.BASE_API_URL))
                .build()

    }

    companion object{
        lateinit var mContext: Context

    }
}