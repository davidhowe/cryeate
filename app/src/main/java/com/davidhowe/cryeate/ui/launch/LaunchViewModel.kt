package com.davidhowe.cryeate.ui.launch

import android.app.Application
import androidx.lifecycle.ViewModel
import com.davidhowe.cryeate.Config
import com.davidhowe.cryeate.base.BaseStateUI
import com.davidhowe.cryeate.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    application: Application) : BaseViewModel(application) {

    override fun onViewCreated() {
        Single.timer(Config.LAUNCH_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _->
                Timber.d("Timer expired")
                liveDataEvent.postValue(BaseStateUI.To(LaunchFragmentDirections.actionLaunchFragmentToMainFragment()))
            }
    }

}