package com.timothy.githublogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timothy.githublogin.model.AuthUserResponse
import com.timothy.githublogin.model.Status
import com.timothy.githublogin.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    val userResponse:LiveData<Status<AuthUserResponse?>>
        get() = _userResponse
    private val _userResponse:MutableLiveData<Status<AuthUserResponse?>> = MutableLiveData()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Exception handled: ${throwable.localizedMessage}")
    }

    private suspend fun getTokenAndSave(code: String) = withContext(Dispatchers.IO){
        repository.getTokenAndSave(code)
    }

    fun getAuth(code:String){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            getTokenAndSave(code)
            val result = repository.getAuthUser()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Status.TokenExpired -> throw Exception("get auth user data error via code")
                    is Status.TokenNormal -> _userResponse.postValue(result)
                }
            }
        }
    }

    fun getAuth(){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = repository.getAuthUser()
            _userResponse.postValue(result)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}