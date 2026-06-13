package com.example.baseandroidapp.feature.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsUserRoute(
    userId: Int,
    viewModel: PdfViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.load(SAMPLE_PDF_BASE64)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsUserScreen(
        uiState = uiState,
        onButtonClick = { /* placeholder - sem ação de negócio */ }
    )
}

@Composable
fun DetailsUserScreen(
    uiState: PdfUiState,
    onButtonClick: () -> Unit
) {
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is PdfUiState.Loading -> CircularProgressIndicator()

                    is PdfUiState.Error ->
                        Text(text = "Não foi possível exibir o PDF.")

                    is PdfUiState.Success ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.pages) { page ->
                                Image(
                                    bitmap = page.asImageBitmap(),
                                    contentDescription = "Página do PDF",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                }
            }

            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Continuar")
            }
        }
    }
}

@Preview(name = "DetailsUserScreenLoading")
@Composable
fun DetailsUserPreview() {
    DetailsUserScreen(uiState = PdfUiState.Loading, onButtonClick = {})
}
