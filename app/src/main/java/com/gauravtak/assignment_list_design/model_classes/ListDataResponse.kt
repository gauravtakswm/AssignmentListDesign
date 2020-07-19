package com.gauravtak.assignment_list_design.model_classes

 class ListDataResponse {
    private var title: String? = null
    private var rows: List<RowsBean?>? = null

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getRows(): List<RowsBean?>? {
        return rows
    }

    fun setRows(rows: List<RowsBean?>?) {
        this.rows = rows
    }

    class RowsBean {
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