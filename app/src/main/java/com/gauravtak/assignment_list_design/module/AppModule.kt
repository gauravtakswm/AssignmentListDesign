package com.assignment.listdesign.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//this is provide app module for dagger implementation with application class
@Module
class AppModule(@get:Provides
                @get:Singleton val application: Application)