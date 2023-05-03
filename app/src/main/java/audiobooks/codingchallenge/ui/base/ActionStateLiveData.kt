package audiobooks.codingchallenge.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import audiobooks.codingchallenge.data.dto.ResponseData
import kotlin.coroutines.CoroutineContext


class ActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    fetchData: (suspend () -> ResponseData)
) {
    private val action = MutableLiveData<Action>()
    private var data: ResponseData? = null // backing data

    val state = action.switchMap {
        liveData(context = coroutineContext) {
            when (action.value) {
                Action.Load -> {
                    emit(UIState.Loading)
                }

                Action.SwipeRefresh -> {
                    emit(UIState.SwipeRefreshing)
                }

                Action.Retry -> {
                    emit(UIState.Retrying)
                }
                else -> {
                    emit(UIState.Loading)
                }
            }

            try {
                val response = fetchData()
                when {
                    response.id !=null -> {
                        data = response
                        emit(UIState.Success(response))
                    }
                    action.value == Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                    }
                    else -> {
                        emit(UIState.Failure(response))
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                when (action.value) {
                    Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(UIState.Success(it))
                        }
                    }
                    else -> {
                        emit(UIState.Failure(data = null))
                    }
                }
            }
        }
    }

    // Helpers for triggering different actions

    fun retry() {
        action.value = Action.Retry
    }

    fun swipeRefresh() {
        action.value = Action.SwipeRefresh
    }

    fun load() {
        action.value = Action.Load
        Log.e("","")
    }
}