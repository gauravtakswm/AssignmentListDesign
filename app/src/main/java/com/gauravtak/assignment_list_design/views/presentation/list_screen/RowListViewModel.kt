package com.gauravtak.assignment_list_design.views.presentation.list_screen

import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper

 class RowListViewModel(private val rowsBean: ListDataResponse.RowsBean) :ViewModel()
{
     val title: ObservableField<String> by lazy{ ObservableField<String>() }
     val description: ObservableField<Spanned> by lazy { ObservableField<Spanned>()}
     val picUrl: ObservableField<String> by lazy { ObservableField<String>() }
     val isVisible:ObservableBoolean by lazy { ObservableBoolean() }
    init {

        if((rowsBean.title==null || rowsBean.title?.isEmpty()!!) && (rowsBean.description==null || rowsBean.description?.isEmpty()!!) && (rowsBean.imageHref==null || rowsBean.imageHref?.isEmpty()!!)) {
            isVisible.set(false) // if every element of list view is null or empty then hide that list Item
        }else {
            isVisible.set(true)
                 title.set(rowsBean.title)
                 if((rowsBean.description==null || rowsBean.description?.isEmpty()!!))
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                         description.set(Html.fromHtml("<b>Description</b> : NA", FROM_HTML_MODE_LEGACY))
                     }else
                     {
                         description.set(Html.fromHtml("<b>Description</b> : NA"))

                     }
                 else
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                         description.set(Html.fromHtml("<b>Description</b> : "+rowsBean.description, FROM_HTML_MODE_LEGACY))
                     }else
                     {
                         description.set(Html.fromHtml("<b>Description</b> : "+rowsBean.description))

                     }

            picUrl.set(rowsBean.imageHref)
        }


    }
    fun performClick(view: View)
    {
        UtilHelper.showToast(title.get()+" Item Clicked")
        //navigate to some other details screen
    }
}