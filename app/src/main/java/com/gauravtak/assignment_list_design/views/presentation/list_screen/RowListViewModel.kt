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

/* this view model is associated with every item of recycler view and their values binding using data binding approach
 the values are binded in quite better way which can be seen in xml of list item(list_item.xml file)
 this single view model class supports both orientation of portrait and landscape*/
 class RowListViewModel(private val rowsBean: ListDataResponse.RowsBean) :ViewModel()
{
    // this title represents the title value of item
     val title: ObservableField<String> by lazy{ ObservableField<String>() }
    // this Description represents the description value of item
    val description: ObservableField<Spanned> by lazy { ObservableField<Spanned>()}
    /*this pic represents the image url value of item( the url image into loaded
     into imageview using glide library inside the ImageSetter class)*/
    val picUrl: ObservableField<String> by lazy { ObservableField<String>() }
    //this boolean is used to set visibility of list item
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