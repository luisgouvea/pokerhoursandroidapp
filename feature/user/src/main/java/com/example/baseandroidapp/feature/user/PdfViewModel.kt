package com.example.baseandroidapp.feature.user

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseandroidapp.core.common.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<PdfUiState>(PdfUiState.Loading)
    val uiState: StateFlow<PdfUiState> = _uiState.asStateFlow()

    /** Decodifica e renderiza o PDF Base64 fora da main thread, atualizando [uiState]. */
    fun load(base64: String) {
        _uiState.value = PdfUiState.Loading
        viewModelScope.launch {
            _uiState.value = runCatching {
                withContext(ioDispatcher) {
                    renderPdfToBitmaps(context, decodeBase64Pdf(base64))
                }
            }.fold(
                onSuccess = { PdfUiState.Success(it) },
                onFailure = { PdfUiState.Error(it) }
            )
        }
    }
}

sealed interface PdfUiState {
    object Loading : PdfUiState
    data class Success(val pages: List<Bitmap>) : PdfUiState
    data class Error(val throwable: Throwable) : PdfUiState
}
