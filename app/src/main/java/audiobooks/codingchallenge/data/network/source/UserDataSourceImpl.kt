package audiobooks.codingchallenge.data.network.source

import audiobooks.codingchallenge.data.dto.ResponseData
import audiobooks.codingchallenge.data.network.ApiService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserDataSource {

    override suspend fun requestGetBestPodCast(): ResponseData {
        return apiService.requestGetBestPodCast().await()
    }

}
