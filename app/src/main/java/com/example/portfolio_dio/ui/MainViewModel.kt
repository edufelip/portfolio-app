package com.example.portfolio_dio.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio_dio.data.models.Repository
import com.example.portfolio_dio.domain.ListRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val listRepositoriesUseCase: ListRepositoriesUseCase
) : ViewModel() {

    private val _repoList = MutableLiveData<State>()
    val repoList: LiveData<State> = _repoList

    fun getRepoList(user: String) {
        viewModelScope.launch {
            listRepositoriesUseCase(user)
                .onStart {
                    _repoList.postValue(State.Loading)
                }
                .catch {
                    _repoList.postValue(State.Error(it))
                }
                .collect {
                    _repoList.postValue(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val list: List<Repository>): State()
        data class Error(val error: Throwable): State()
    }
}