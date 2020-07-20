package com.assignment.listdesign.di.module


import com.gauravtak.assignment_list_design.views.presentation.list_screen.fragments.ListFragmentViewModel
import dagger.Component
import javax.inject.Singleton

//this interface is used to define every inject based on Activity fragments or viewModel
@Singleton
@Component(modules = [ApiClientModule::class, AppModule::class])
interface ApiComponent {
    fun inject(listFragmentViewModel: ListFragmentViewModel?)
}