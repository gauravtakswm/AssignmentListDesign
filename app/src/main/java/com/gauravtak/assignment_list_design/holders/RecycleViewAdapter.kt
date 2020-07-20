package com.gauravtak.assignment_list_design.holders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gauravtak.assignment_list_design.R
import com.gauravtak.assignment_list_design.databinding.ListItemBinding
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.views.presentation.list_screen.RowListViewModel



/* this Adapter class is designed to hold the all the item of recycler view and data-values has been binding on screen
 using RowListViewModel and list_item.xml file*/
class RecycleViewAdapter(private val activity: Activity, private var rowsBeanList: ArrayList<ListDataResponse.RowsBean?>)
    : RecyclerView.Adapter<RecycleViewAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val listItemBinding: ListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context),
                R.layout.list_item, viewGroup, false) as ListItemBinding
        return ItemViewHolder(listItemBinding);
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dataBean: ListDataResponse.RowsBean = rowsBeanList[position]!!
        holder.bind(dataBean)


    }

    class ItemViewHolder(listItemBinding: ListItemBinding) : RecyclerView.ViewHolder(listItemBinding.getRoot()) {
        var mBinding: ListItemBinding? = null

        // var mBinding: ListItemScheduleMeetingBinding? = null
        fun bind(dataBean: ListDataResponse.RowsBean) {
            mBinding?.executePendingBindings()
            val rowListViewModel = RowListViewModel(dataBean)
            mBinding?.rowListViewModel = rowListViewModel;

            }

        init {
            mBinding = listItemBinding
        }
    }

    override fun getItemCount(): Int = rowsBeanList.size


    fun setData(data: ArrayList<ListDataResponse.RowsBean?>) {
        this.rowsBeanList = data
        notifyDataSetChanged()
    }
}