package audiobooks.codingchallenge.data.repository

import audiobooks.codingchallenge.data.dto.ResponseData

interface UserRepository {

    suspend fun getBestPodCastResponse(): ResponseData
}