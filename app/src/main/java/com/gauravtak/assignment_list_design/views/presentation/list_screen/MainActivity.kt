package com.gauravtak.assignment_list_design.views.presentation.list_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.gauravtak.assignment_list_design.R
import com.gauravtak.assignment_list_design.views.presentation.list_screen.fragments.ListFragment
import kotlinx.android.synthetic.main.activity_main.*


//the Activity represents the activity screen which is showing list item which are populated into recycler View
//Activity name can be given based on the purpose or screen use
class MainActivity : AppCompatActivity() {

    private val fragmentTransaction: FragmentTransaction by lazy { supportFragmentManager.beginTransaction() }
    private var listFragment: ListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        loadListFragment()
    }

    private fun loadListFragment() {
        if (listFragment == null) {
            listFragment = ListFragment.newInstance()
        }
        fragmentTransaction.replace(R.id.fragment_container, listFragment!!).commit()
        //fragments can be added to backStack using addToBackStack as per the need of flow.
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }


}