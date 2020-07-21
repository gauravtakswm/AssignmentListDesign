package com.gauravtak.assignment_list_design.views.presentation.list_screen.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gauravtak.assignment_list_design.R
import com.gauravtak.assignment_list_design.databinding.FragmentListBinding
import com.gauravtak.assignment_list_design.holders.RecycleViewAdapter
import com.gauravtak.assignment_list_design.model_classes.ListDataResponse
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper
import com.gauravtak.assignment_list_design.views.custom_views.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var listDataResponse: ListDataResponse
    private lateinit var fragmentListBinding: FragmentListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var listFragmentViewModel: ListFragmentViewModel

    private lateinit var arrayListRowsBean:ArrayList<ListDataResponse.RowsBean?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    //menu has been added to add refresh icon into menu, to refresh the list any time.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_screen_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            //this is called when refresh icon is clicked
            R.id.action_refresh->{

               if(UtilHelper.isConnectToInternet(context))
                   listFragmentViewModel.getListDataApiCall()
                else
                   UtilHelper.showSnackBar(activity!!,getString(R.string.please_check_your_internet_connection))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       fragmentListBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false)
        //fragmentListBinding = DataBindingUtil.setContentView(activity, R.layout.activity_main)
        //in the layout file, toolbar added , swipe refresh layout added and recycler view added
        fragmentListBinding.executePendingBindings()
        return fragmentListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initViewModel()

        if(UtilHelper.isConnectToInternet(context))
            listFragmentViewModel.getListDataApiCall()
        else
            UtilHelper.showSnackBar(activity!!,getString(R.string.please_check_your_internet_connection))
    }
    private fun setUpRecyclerView() {
        //initially the recycler view is populated with blank list
        arrayListRowsBean = ArrayList()
        recycleViewAdapter = RecycleViewAdapter(arrayListRowsBean)
        fragmentListBinding.recyclerView.adapter = recycleViewAdapter
    }
    private fun initViewModel() {
        //initialization of view model is done to perform business logic tasks and other presentation layer logic operations
        listFragmentViewModel = ViewModelProviders.of(this).get(ListFragmentViewModel::class.java)
        fragmentListBinding.listViewModel = listFragmentViewModel

        listFragmentViewModel.init()

        listFragmentViewModel.showMessage.observe(this, Observer<Any?> { obj ->
            if(activity!=null)
            UtilHelper.showSnackBar(activity!!,obj as String)
        })
        listFragmentViewModel.showProgress.observe(this, Observer<Any?> {
            if(activity!=null)
            CustomProgressDialog.showProgress(activity!!)
        })
        listFragmentViewModel.hideProgress.observe(this, Observer<Any?> {
            CustomProgressDialog.hideProgress()
        })
        listFragmentViewModel.listResponseDataEvent.observe(this, Observer<Any?> { obj ->
            try {
                //after getting api data as response , recycler view list updated and notify the list with new list

                listDataResponse = obj as ListDataResponse
                activity?.toolbar?.title = listDataResponse.getTitle()
                //Updating the title of toolbar based on api response or internal stored data

                arrayListRowsBean = listDataResponse.getRows()!!
                recycleViewAdapter.setData(arrayListRowsBean)

            }catch (e: Exception){
                e.stackTrace
            }

        })

    }
    companion object {


        @JvmStatic
        fun newInstance() =
                ListFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}