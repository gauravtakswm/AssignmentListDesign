package com.gauravtak.assignment_list_design.views.presentation.list_screen


import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper

/* this view model is associated with every item of recycler view and their values binding using data binding approach
 the values are binded in quite better way which can be seen in xml of list item(list_item.xml file)
 this single view model class supports both orientation of portrait and landscape*/
class RowListViewModel(rowsBean: ListDataResponse.RowsBean) : ViewModel() {
    // this title represents the title value of item
    val title: ObservableField<String> by lazy { ObservableField<String>() }

    // this Description represents the description value of item
    val description: ObservableField<String> by lazy { ObservableField<String>() }

    /*this pic represents the image url value of item( the url image into loaded
     into imageview using glide library inside the ImageSetter class)*/
    val picUrl: ObservableField<String> by lazy { ObservableField<String>() }

    //this boolean is used to set visibility of list item
    val isVisible: ObservableBoolean by lazy { ObservableBoolean() }

    // as per the need visibility and size of views will be handled according to the need
    init {

        if (UtilHelper.isRowVisible(rowsBean)) {
            isVisible.set(true)
            title.set(rowsBean.title)
            description.set(rowsBean.description)
            picUrl.set(rowsBean.imageHref)
        } else {
            isVisible.set(false) // if every element of list view is null or empty then hide that list Item
        }


    }

    fun performClick() {
        UtilHelper.showToast(title.get() + " Item Clicked")
        //navigate to some other details screen
    }
}