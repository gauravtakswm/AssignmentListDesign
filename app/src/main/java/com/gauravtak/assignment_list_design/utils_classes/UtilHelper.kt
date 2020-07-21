package com.gauravtak.assignment_list_design.utils_classes

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.gauravtak.assignment_list_design.AssignmentListDesignApp
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.google.android.material.snackbar.Snackbar

object UtilHelper {

    fun showSnackBar(mActivity: FragmentActivity, message: String) {

        Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
     fun showToast(message: String) {
        Toast.makeText(AssignmentListDesignApp.mContext, message, Toast.LENGTH_LONG).show()
    }
    fun isConnectToInternet(context: Context?): Boolean {
        val connManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connManager.activeNetworkInfo
        return info?.isConnected ?: false
    }

    fun isRowVisible(rowsBean: ListDataResponse.RowsBean): Boolean {
        return !((rowsBean.title == null || rowsBean.title?.trim()?.isEmpty()!!) && (rowsBean.description == null || rowsBean.description?.trim()?.isEmpty()!!) && (rowsBean.imageHref == null || rowsBean.imageHref?.trim()?.isEmpty()!!))
    }
    fun isImageViewVisible(imageUrl:String?): Boolean {
        return !(imageUrl == null || imageUrl.trim().isEmpty())
    }

}