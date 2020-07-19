package com.gauravtak.assignment_list_design.views.custom_views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.gauravtak.assignment_list_design.R

object CustomProgressDialog {
    private  var dialog: Dialog? = null
    fun showProgress(mcontext: Context?) {
        try {
            if (dialog == null) {
                dialog = Dialog(mcontext)
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog?.setCanceledOnTouchOutside(false)
                dialog?.setContentView(R.layout.dialog_progressbar)
            }
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideprogressbar() {
        if (dialog?.isShowing!!) {
            dialog?.dismiss()
            dialog = null
        }
    }
}