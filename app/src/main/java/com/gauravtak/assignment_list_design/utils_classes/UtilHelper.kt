package com.gauravtak.assignment_list_design.utils_classes

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.gauravtak.assignment_list_design.AssignmentListDesignApp
import com.google.android.material.snackbar.Snackbar

object UtilHelper {
     fun showSnackBar(mActivity: AppCompatActivity, message: String) {
         if(mActivity!=null)
             Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
    fun showSnackBar(mActivity: FragmentActivity, message: String) {
        if(mActivity!=null)
        Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
     fun showToast(message: String) {
        Toast.makeText(AssignmentListDesignApp.mContext, message, Toast.LENGTH_LONG).show()
    }
    fun isConnectToInternet(activity: Context): Boolean {
        val connManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connManager.activeNetworkInfo
        return if (info != null) if (info.isConnected) {
            true
        } else {
            false
        } else false
    }

}