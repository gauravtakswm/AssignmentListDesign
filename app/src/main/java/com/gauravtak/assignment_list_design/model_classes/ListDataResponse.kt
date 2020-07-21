package com.gauravtak.assignment_list_design.model_classes

import java.io.Serializable

//this class is used to received the data from api as per the required json format
 class ListDataResponse :Serializable{
    private var title: String? = null
    private var rows: ArrayList<RowsBean?>? = null

    fun getTitle(): String? {
        return title
    }

   fun getRows(): ArrayList<RowsBean?>? {
        return rows
    }

    class RowsBean :Serializable{
        /**
         * title : Beavers
         * description : Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony
         * imageHref : http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg
         */
        var title: String? = null
        var description: String? = null
        var imageHref: String? = null

    }
}