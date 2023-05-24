package com.kath.randomapiconsumer.ui.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kath.randomapiconsumer.domain.model.RandomUser
import com.kath.randomapiconsumer.ui.details.DetailsActivity
import com.kath.randomapiconsumer.ui.list.components.ErrorScreen
import com.kath.randomapiconsumer.ui.list.components.LoadingScreen
import com.kath.randomapiconsumer.ui.list.components.TopBarList
import com.kath.randomapiconsumer.ui.theme.RandomApiConsumerTheme
import org.koin.androidx.compose.koinViewModel

class ListActivity : ComponentActivity() {
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = koinViewModel()
            RandomApiConsumerTheme {
                LaunchedEffect(Unit) {
                    viewModel.getUsersList()
                }
                ListScreen(uiState = viewModel.users.collectAsState().value)
            }
        }
    }

    @Composable
    fun ListScreen(uiState: ListUiState) {

        when (uiState) {
            is ListUiState.Loading -> {
                LoadingScreen()
            }
            is ListUiState.Success -> {
                ListScreenSuccess(randomUsers = uiState.randomUsers)
            }
            is ListUiState.Error -> {
                ErrorScreen()
            }
        }
    }

    @Composable
    fun ListScreenSuccess(randomUsers: List<RandomUser>) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                TopBarList(
                    orderByLastName = { viewModel.orderByLastName(randomUsers) },
                    orderByAge = { viewModel.orderByAge(randomUsers) },
                    orderByDefault = { viewModel.byDefault() }
                )
            },
            content = {
                LazyColumn {
                    items(randomUsers) { user ->
                        UserRow(user)
                    }
                }
            })
    }

    @Composable
    fun UserRow(user: RandomUser) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                startActivity(
                    DetailsActivity.getIntent(
                        context = applicationContext,
                        user = user
                    )
                )
            }) {
            AsyncImage(
                model = user.pictureMedium,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .padding(10.dp)
            )
            Text(text = user.name,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp))
        }
    }
}