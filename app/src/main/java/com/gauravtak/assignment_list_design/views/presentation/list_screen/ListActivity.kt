package com.gauravtak.assignment_list_design.views.presentation.list_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gauravtak.assignment_list_design.AssignmentListDesignApp
import com.gauravtak.assignment_list_design.R
import com.gauravtak.assignment_list_design.databinding.ActivityListBinding
import com.gauravtak.assignment_list_design.db_storage.DatabaseHandler
import com.gauravtak.assignment_list_design.holders.RecycleViewAdapter
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.AppConstants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list.*
import java.lang.Exception

//the Activity represents the activity screen which is showing list item which are populated into recycler View
//Activity name can be given based on the purpose or screen use
class ListActivity : AppCompatActivity() {
    private val mActivity: AppCompatActivity by lazy {this}
    private lateinit var listDataResponse: ListDataResponse
    private lateinit var activityListBinding: ActivityListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var listActivityViewModel: ListActivityViewModel

    private lateinit var arrayListRowsBean:ArrayList<ListDataResponse.RowsBean?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        //in the layout file, toolbar added , swipe refresh layout added and recycler view added
        activityListBinding.executePendingBindings()
        setSupportActionBar(toolbar)
        setUpRecyclerView()
        initViewModel()
        Handler().postDelayed(Runnable { listActivityViewModel.getListDataApiCall()  },5000)
    }



    //menu has been added to add refresh icon into menu, to refresh the list any time.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //menu item click events added here
        when(item?.itemId)
        {
            //this is called when refresh icon is clicked
            R.id.action_refresh->{
                listActivityViewModel.getListDataApiCall()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setUpRecyclerView() {
        //initially the recycler view is populated with blank list
        arrayListRowsBean = ArrayList()
        recycleViewAdapter = RecycleViewAdapter(mActivity, arrayListRowsBean)
        activityListBinding.recyclerView.adapter = recycleViewAdapter
    }

    private fun initViewModel() {
        //initialization of view model is done to perform business logic tasks and other presentation layer logic operations
        listActivityViewModel = ViewModelProviders.of(this).get(ListActivityViewModel::class.java)
        activityListBinding.listViewModel = listActivityViewModel

        listActivityViewModel.init(this)

        listActivityViewModel.listResponseDataEvent.observe(this, Observer<Any?> { obj ->
           try {
               //after getting api data as response , recycler view list updated and notify the list with new list
                listDataResponse = obj as ListDataResponse
               arrayListRowsBean = listDataResponse.getRows()!!
               recycleViewAdapter.setData(arrayListRowsBean)

           }catch (e:Exception){
               e.stackTrace
           }
        })

    }


}