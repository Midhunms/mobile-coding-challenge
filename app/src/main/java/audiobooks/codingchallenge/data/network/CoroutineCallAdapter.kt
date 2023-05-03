package audiobooks.codingchallenge.data.network

import android.util.Log
import audiobooks.codingchallenge.data.dto.ResponseData
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutineCallAdapter private constructor() : CallAdapter.Factory() {
    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = CoroutineCallAdapter()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == Response::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Response must be parameterized as Response<Foo> or Response<out Foo>"
                )
            }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    private class BodyCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<T> {
            val deferred = CompletableDeferred<T>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val code = response.code()
                    when (code) {
                        in 200..299 -> {
                            if (response.body() != null) {
                                deferred.complete(response.body()!!)
                            }/*else{
                                Log.e("","")
                                val res = ResponseData<String>().apply {
                                    this.data = "Success"
                                }
                                deferred.complete((res) as T)
                                Log.e("","")
                            }*/

                        }
                        401 -> {
                            deferred.completeExceptionally(HttpException(response))
                        }
                        in 400..499 -> {
                            deferred.completeExceptionally(HttpException(response))
                        }
                        in 500..599 -> {
                            deferred.completeExceptionally(HttpException(response))
                        }
                        else -> {
                            deferred.completeExceptionally(HttpException(response))
                        }
                    }

                }
            })

            return deferred
        }
    }

    private class ResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<Response<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<Response<T>> {
            val deferred = CompletableDeferred<Response<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    deferred.complete(response)
                }
            })

            return deferred
        }
    }
}