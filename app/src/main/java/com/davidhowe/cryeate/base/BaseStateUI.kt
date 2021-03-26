package com.davidhowe.cryeate.base

import androidx.navigation.NavDirections
import com.davidhowe.cryeate.R
import java.lang.ref.WeakReference

sealed class BaseStateUI {
    //NAVIGATION STATES (Ref https://medium.com/google-developer-experts/using-navigation-architecture-component-in-a-large-banking-app-ac84936a42c2)
    enum class ErrorStates {NETWORK_ERROR}
    data class To(val directions: NavDirections): BaseStateUI()
    object Back: BaseStateUI()
    data class BackTo(val destinationId: Int): BaseStateUI()
    object ToRoot: BaseStateUI()
    data class ToastMessage(val messageResId: Int): BaseStateUI()
    data class ErrorDialog(val errorState: ErrorStates, val listener : WeakReference<BaseFragment.DialogClickListener>, val positiveButtonResId: Int = R.string.dialog_text_ok): BaseStateUI()
    //TODO OTHER UI STATES
}

