package com.davidhowe.cryeate.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.davidhowe.cryeate.App.Companion.appComponent
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.di.AppComponent
import com.davidhowe.cryeate.di.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), ViewModelProvider.Factory {
    abstract val viewModelClass : Class<VM>
    val viewModel : VM by lazy { provideViewModel() }
    @Inject
    lateinit var mProvider: Provider<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appComponent)
    }

    abstract fun inject(appComponent: AppComponent)

    open fun provideViewModel(): VM {
        return ViewModelProvider(
            this,
            ViewModelFactory(mProvider)
        ).get(viewModelClass)
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
        viewModel.load()
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
                    findNavController().navigateUp()
                }
                is BaseStateUI.To -> {
                    Timber.d("state To: ${state.directions}")
                    findNavController().navigate(state.directions)
                }
                is BaseStateUI.ErrorDialog -> {
                    when(state.errorState) {
                        BaseStateUI.ErrorStates.NETWORK_ERROR -> {
                            MaterialDialog(this.context!!)
                                .cancelOnTouchOutside(false)
                                .title(R.string.text_network_error_title)
                                .message(R.string.text_network_error_message)
                                .show {
                                icon(R.drawable.ic_network_error)
                                positiveButton(state.positiveButtonResId) {
                                    state.listener.get()?.onPosClicked()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    interface DialogClickListener {
        fun onPosClicked()
    }
}