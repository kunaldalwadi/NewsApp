package com.example.newsapp.ui.viewmodel

import kotlinx.coroutines.delay

class LoginViewModel(
) : BaseViewModel() {

    init {
        // Check if the user is already logged in
        // If so, navigate to the news screen

    }

    fun goToNewsScreen() {
        // Simulate a login process
        launchSafely(showLoading = true) {
            delay(2000) // Simulate network delay
        }
    }
}