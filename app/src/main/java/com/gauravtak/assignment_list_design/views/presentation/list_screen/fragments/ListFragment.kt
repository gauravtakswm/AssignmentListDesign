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
import kotlinx.android.synthetic.main.activity_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listDataResponse: ListDataResponse
    private lateinit var fragmentListBinding: FragmentListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var listFragmentViewModel: ListFragmentViewModel

    private lateinit var arrayListRowsBean:ArrayList<ListDataResponse.RowsBean?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    //menu has been added to add refresh icon into menu, to refresh the list any time.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_screen_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId)
        {
            //this is called when refresh icon is clicked
            R.id.action_refresh->{

               if(UtilHelper.isConnectToInternet(activity!!))
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
        val view: View? = fragmentListBinding.getRoot()
       return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
        initViewModel()

        if(UtilHelper.isConnectToInternet(activity!!))
                listFragmentViewModel.getListDataApiCall()
        else
            UtilHelper.showSnackBar(activity!!,getString(R.string.please_check_your_internet_connection))

        super.onViewCreated(view, savedInstanceState)
    }
    private fun setUpRecyclerView() {
        //initially the recycler view is populated with blank list
        arrayListRowsBean = ArrayList()
        recycleViewAdapter = RecycleViewAdapter(activity!!, arrayListRowsBean)
        fragmentListBinding.recyclerView.adapter = recycleViewAdapter
    }
    private fun initViewModel() {
        //initialization of view model is done to perform business logic tasks and other presentation layer logic operations
        listFragmentViewModel = ViewModelProviders.of(this).get(ListFragmentViewModel::class.java)
        fragmentListBinding.listViewModel = listFragmentViewModel

        listFragmentViewModel.init(activity!!)

        listFragmentViewModel.listResponseDataEvent.observe(this, Observer<Any?> { obj ->
            try {

                //after getting api data as response , recycler view list updated and notify the list with new list

                listDataResponse = obj as ListDataResponse
                activity?.toolbar?.title = listDataResponse.getTitle();
                //Updating the title of toolbar based on api response or internal stored data

                arrayListRowsBean = listDataResponse.getRows()!!
                recycleViewAdapter.setData(arrayListRowsBean)

            }catch (e: Exception){
                e.stackTrace
            }
        })

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                ListFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}