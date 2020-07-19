package com.assignment.listdesign.di.module


import com.gauravtak.assignment_list_design.views.presentation.list_screen.ListActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiClientModule::class, AppModule::class])
interface ApiComponent {
   // fun inject(mainActivity: MeetingsListingActivity?)
   // fun inject(listActivity: MainActivity?)
    fun inject(listActivityViewModel: ListActivityViewModel?)


}