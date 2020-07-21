package com.gauravtak.assignment_list_design.utils_classes

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

     override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {

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

}