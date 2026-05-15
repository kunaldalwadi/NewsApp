package com.example.newsapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: String? = _errorMessage.value

    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    protected fun setError(message: String?) {
        _errorMessage.value = message
    }

     protected fun clearError() {
        _errorMessage.value = null
    }

    protected fun launchSafely(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        showLoading: Boolean = false,
        onError: (Throwable) -> Unit = { throwable ->
            setError(throwable.message ?: "An unknown error occurred")
        },
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                if (showLoading) setLoading(true)
                clearError()
                block()
            } catch (cancel: CancellationException) {
                throw cancel
            } catch (t: Throwable) {
                onError(t)
            } finally {
                if (showLoading) setLoading(false)
            }
        }
    }

}