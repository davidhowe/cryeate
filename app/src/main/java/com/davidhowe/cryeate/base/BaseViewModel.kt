package com.davidhowe.cryeate.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.davidhowe.cryeate.extensions.SingleLiveEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataEvent = SingleLiveEvent<BaseStateUI>()
    open fun onViewCreated() {}
}