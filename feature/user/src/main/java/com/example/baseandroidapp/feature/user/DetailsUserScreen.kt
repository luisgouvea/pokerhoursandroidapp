package com.example.baseandroidapp.feature.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailsUserRoute(
    userId: Int
) {
    DetailsUserScreen(userId)
}

@Composable
fun DetailsUserScreen(userId: Int) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Id User: $userId")
        }
    }
}

@Preview(name = "DetailsUserScreenPopulated")
@Composable
fun DetailsUserPreview() {
    DetailsUserScreen(19)
}