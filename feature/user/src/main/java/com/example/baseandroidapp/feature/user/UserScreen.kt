package com.example.baseandroidapp.feature.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.baseandroidapp.core.designsystem.icon.BaaIcons
import com.example.baseandroidapp.core.model.data.User

@Composable
fun UserRoute(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userUiState by viewModel.uiUserState.collectAsStateWithLifecycle()
    UserScreen(userUiState, onNavigateToDetails)
}

@Composable
fun UserScreen(
    userUiState: UserUiState,
    onNavigateToDetails: (Int) -> Unit
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (userUiState) {
                UserUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UserUiState.Success -> {
                    if (userUiState.users.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(userUiState.users) { user ->
                                ListItem(
                                    name = user.name,
                                    id = user.id,
                                    onNavigateToDetails
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    name: String,
    id: Int,
    onNavigateToDetails: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetails(id) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = BaaIcons.Person,
            contentDescription = "Person",
            modifier = Modifier.size(100.dp),
            tint = Color(MaterialTheme.colorScheme.tertiaryContainer.toArgb())
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            style = typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = BaaIcons.ArrowForwardIos,
            contentDescription = "Arrow"
        )


    }
    HorizontalDivider()
}

@Preview(name = "UserScreenPopulated")
@Composable
fun UserScreenPreview() {
    val userUiState: UserUiState = UserUiState.Success(mutableListOf(User(1, "First name")))
    UserScreen(userUiState,{})
}

@Preview(name = "ListItem", showBackground = true)
@Composable
fun ListItemPreview() {
    ListItem("Name",1,{})
}