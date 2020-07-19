package com.gauravtak.assignment_list_design.utils_classes

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gauravtak.assignment_list_design.AssignmentListDesignApp
import com.google.android.material.snackbar.Snackbar

object UtilHelper {
     fun showSnackBar(mActivity: AppCompatActivity, message: String) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
     fun showToast(message: String) {
        Toast.makeText(AssignmentListDesignApp.mContext, message, Toast.LENGTH_LONG).show()
    }

}