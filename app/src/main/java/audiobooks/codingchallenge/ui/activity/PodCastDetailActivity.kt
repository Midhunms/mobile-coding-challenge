package audiobooks.codingchallenge.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import audiobooks.codingchallenge.data.dto.PodCastData
import audiobooks.codingchallenge.ui.base.ScopedActivity
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivityPodCastDetailBinding
import com.audiobooks.codingchallenge.databinding.ContentPodcastDetailBinding
import com.audiobooks.codingchallenge.databinding.IncludeAppBarLayoutWhiteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PodCastDetailActivity : ScopedActivity() {
    private lateinit var dataBinding: ActivityPodCastDetailBinding
    private lateinit var contentDetailDataBinding: ContentPodcastDetailBinding
    private lateinit var contentAppBarDataBinding: IncludeAppBarLayoutWhiteBinding
    private var podcastItem: PodCastData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        podcastItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("DETAIL_VIEW", PodCastData::class.java)!!
        } else {
            intent.extras?.getParcelable<PodCastData>("DETAIL_VIEW")!!
        }
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_pod_cast_detail)
        contentDetailDataBinding = dataBinding.contentContainer
        contentAppBarDataBinding = dataBinding.appBarContainer

        contentDetailDataBinding.titleText.text = podcastItem?.title?:"NA"
        contentDetailDataBinding.authorNameText.text = podcastItem?.publisher?:"NA"
        contentDetailDataBinding.detailsText.text = podcastItem?.description?:"NA"
        contentDetailDataBinding.simpleDraweeView.setImageURI(podcastItem?.thumbnail)

        if(podcastItem?.isFavourite == true){
            contentDetailDataBinding.isFavouriteStatusText.text = "Favourited"
        }else{
            contentDetailDataBinding.isFavouriteStatusText.text = "Favourite"
        }

        contentAppBarDataBinding.imgBackButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("DETAIL_VIEW_UPDATED", podcastItem?.isFavourite)
            this.setResult(Activity.RESULT_OK, intent)

            finish()
        }

        contentDetailDataBinding.isFavouriteStatusText.setOnClickListener {
            if(podcastItem?.isFavourite == true){
                podcastItem?.isFavourite = false
                contentDetailDataBinding.isFavouriteStatusText.text = "Favourite"
            }else{
                podcastItem?.isFavourite = true
                contentDetailDataBinding.isFavouriteStatusText.text = "Favourited"
            }
        }
    }

    override fun onBackPressed() {


        val intent = Intent()
        intent.putExtra("DETAIL_VIEW_UPDATED", podcastItem?.isFavourite)
        this.setResult(Activity.RESULT_OK, intent)
        finish()
        super.getOnBackPressedDispatcher()
    }
}