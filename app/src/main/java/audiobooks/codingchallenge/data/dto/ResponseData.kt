package audiobooks.codingchallenge.data.dto

data class ResponseData(
    val id: Int,
    val total: Int,
    val name: String,
    val has_next: Boolean,
    val podcasts: ArrayList<PodCastData>,

    )
data class PodCastData(
    val isFavourite: Boolean,
    val id: String,
    val thumbnail: String,
    val title: String,
    val publisher: String,
    val description: String,

    )