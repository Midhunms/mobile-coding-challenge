package audiobooks.codingchallenge.data.network

import audiobooks.codingchallenge.data.dto.ResponseData
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

public interface ApiService {

    @GET(RequestURL.SERVICE_BEST_PODCAST)
    fun requestGetBestPodCast(): Deferred<ResponseData>

    companion object {

        operator fun invoke(
        ): ApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    val request = chain.request()
                    val requestBuilder = request.newBuilder()
                    val mBuilder = requestBuilder
                        .addHeader("X-ListenAPI-FreeQuota", "25000")
                        .addHeader("X-ListenAPI-Usage", "19231")
                        .addHeader("X-listenAPI-Latency-Seconds", "0.056")
                        .addHeader("X-Listenapi-NextBillingDate", "2020-09-26T17:27:33.110641+00:00")
                    chain.proceed(mBuilder.build())
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://listen-api-test.listennotes.com/api/v2/")
                .addCallAdapterFactory(CoroutineCallAdapter())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }
}