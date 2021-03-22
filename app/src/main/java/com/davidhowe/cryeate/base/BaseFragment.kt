package com.davidhowe.cryeate.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davidhowe.cryeate.App.Companion.appComponent
import com.davidhowe.cryeate.di.AppComponent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), ViewModelProvider.Factory {
    abstract val viewModelClass : Class<VM>
    private val viewModel : VM by lazy { provideViewModel() }
    @Inject
    lateinit var mProvider: Provider<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appComponent)
    }

    abstract fun inject(appComponent : AppComponent)

    open fun provideViewModel(): VM {
        return ViewModelProvider(this).get(viewModelClass)
    }

    /**
     * Function performs
     * 1. Connect the VM emitting SingleLiveEvent states to the Observer actioning the emitted events on the Fragment view
     * 2. Calls VM onViewCreated() fun allowing VM to initiate any required onLoad functionality
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveDataEvent.observe(
            viewLifecycleOwner,
            baseStateUIObserver
        )
        viewModel.onViewCreated()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Override ViewModelProvider create function to facilitate VM injection
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return mProvider.get() as T
    }

    /**
     * Live data observer to action BaseStateUI state requests
     */
    private val baseStateUIObserver = Observer<BaseStateUI> { state ->
        state?.let {
            when (state) {
                BaseStateUI.Back -> {
                    Timber.d("state Back")
                }
                is BaseStateUI.BackTo -> {
                    Timber.d("state BackTo: ${state.destinationId}")
                }
                is BaseStateUI.To -> {
                    Timber.d("state To: ${state.directions}")
                    findNavController().navigate(state.directions)
                }
                BaseStateUI.ToRoot -> {
                    Timber.d("state ToRoot")
                }
            }
        }
    }
}