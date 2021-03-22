package com.davidhowe.cryeate.base

import androidx.lifecycle.AndroidViewModel
import com.davidhowe.cryeate.App
import com.davidhowe.cryeate.extensions.SingleLiveEvent

abstract class BaseViewModel(application: App) : AndroidViewModel(application) {
    val liveDataEvent = SingleLiveEvent<BaseStateUI>()
    open fun onViewCreated() {}
}