package com.davidhowe.cryeate.base

import androidx.navigation.NavDirections

sealed class BaseStateUI {
    //NAVIGATION STATES (Ref https://medium.com/google-developer-experts/using-navigation-architecture-component-in-a-large-banking-app-ac84936a42c2)
    data class To(val directions: NavDirections): BaseStateUI()
    object Back: BaseStateUI()
    data class BackTo(val destinationId: Int): BaseStateUI()
    object ToRoot: BaseStateUI()
    //TODO OTHER UI STATES
}

