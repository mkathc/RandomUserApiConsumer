package com.kath.randomapiconsumer.ui.list

import androidx.lifecycle.ViewModel
import com.kath.randomapiconsumer.domain.model.RandomUser
import com.kath.randomapiconsumer.domain.usecase.GetRandomUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(private val getRandomUsers: GetRandomUsersUseCase) : ViewModel(){

    private val usersListState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val users = usersListState.asStateFlow()

    var usersByDefault : List<RandomUser> = mutableListOf()

    suspend fun getUsersList(){
        val list = getRandomUsers.invoke()
        if (list.isNotEmpty()){
            usersByDefault = list
            usersListState.value = ListUiState.Success(list)
        }else{
            usersListState.value = ListUiState.Error
        }
    }

    fun orderByLastName(list: List<RandomUser>){
        usersListState.value = ListUiState.Loading
        val sortedAppsList = list.sortedBy { it.lastName}
        usersListState.value = ListUiState.Success(sortedAppsList)
    }


    fun orderByAge(list: List<RandomUser>){
        usersListState.value = ListUiState.Loading
        val sortedAppsList = list.sortedBy { it.age}
        usersListState.value = ListUiState.Success(sortedAppsList)
    }


    fun byDefault(){
        usersListState.value = ListUiState.Loading
        usersListState.value = ListUiState.Success(usersByDefault)
    }
}

