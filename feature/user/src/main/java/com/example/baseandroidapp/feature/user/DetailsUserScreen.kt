package com.example.baseandroidapp.feature.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseandroidapp.core.designsystem.icon.BaaIcons

@Composable
fun DetailsUserRoute(
    userId: Int
) {
    DetailsUserScreen(userId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsUserScreen(userId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details User $userId") },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = BaaIcons.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Button(
                onClick = {}
            ) {
                Text(text = "Payment Pix")
            }
        }
    }
}

@Preview(name = "DetailsUserScreenPopulated")
@Composable
fun DetailsUserPreview() {
    DetailsUserScreen(19)
}