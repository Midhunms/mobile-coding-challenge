package audiobooks.codingchallenge.data.repository

import audiobooks.codingchallenge.data.dto.ResponseData
import audiobooks.codingchallenge.data.network.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
) : UserRepository {
    override suspend fun getBestPodCastResponse(): ResponseData {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.requestGetBestPodCast()
        }
    }

}