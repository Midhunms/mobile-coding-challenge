package audiobooks.codingchallenge.ui.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audiobooks.codingchallenge.data.repository.UserRepository
import audiobooks.codingchallenge.ui.base.ActionStateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val getBestPodCastResponse =
        ActionStateLiveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.getBestPodCastResponse()
        }

    fun processBestPodCastList() {
        getBestPodCastResponse.load()
    }
}