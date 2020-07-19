package com.gauravtak.assignment_list_design.utils_classes

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataEvent<T> : MutableLiveData<T>() {
    val mPending = AtomicBoolean(false)

     override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
         }
        super.observe(lifecycleOwner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}