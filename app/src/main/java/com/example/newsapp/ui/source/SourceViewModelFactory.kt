package com.example.newsapp.ui.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.domain.repository.SourceRepository

class SourceViewModelFactory(
    private val sourceRepository: SourceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SourceViewModel(sourceRepository = sourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}