package com.davidhowe.cryeate.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.base.BaseFragment
import com.davidhowe.cryeate.di.AppComponent

class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModelClass = MainViewModel::class.java
    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
}