package audiobooks.codingchallenge.data.network.source

import audiobooks.codingchallenge.data.dto.ResponseData

interface UserDataSource {

    suspend fun requestGetBestPodCast(): ResponseData

}