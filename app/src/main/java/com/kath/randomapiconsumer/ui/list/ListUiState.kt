package com.kath.randomapiconsumer.ui.list

import com.kath.randomapiconsumer.domain.model.RandomUser

sealed class ListUiState{
    object Loading: ListUiState()
    data class Success(val randomUsers: List<RandomUser>): ListUiState()
    object Error: ListUiState()
}
