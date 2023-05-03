package audiobooks.codingchallenge.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import audiobooks.codingchallenge.adapter.PodCastAdapter
import audiobooks.codingchallenge.data.dto.PodCastData
import audiobooks.codingchallenge.ui.base.ScopedActivity
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivityDashboardBinding
import com.audiobooks.codingchallenge.databinding.ContentDashboardBinding
import com.audiobooks.codingchallenge.databinding.IncludeAppBarLayoutWhiteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ScopedActivity(), PodCastAdapter.PodCastItemClickListener {

    private lateinit var dataBinding: ActivityDashboardBinding
    private lateinit var contentDashboardDataBinding: ContentDashboardBinding
    private lateinit var contentAppBarDataBinding: IncludeAppBarLayoutWhiteBinding
    private var podcastList: ArrayList<PodCastData> = ArrayList()
    private var mAdapter: PodCastAdapter? = null
    private var podcastItem: PodCastData? = null
    private var mSelectedPosition: Int = -1
    private var openPodCastDetailViewLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                if (data != null) {
                    podcastList[mSelectedPosition].isFavourite = data.extras?.getBoolean("DETAIL_VIEW_UPDATED",false)!!
                    mAdapter?.notifyDataSetChanged()
                }

            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        podcastList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("PODCAST_DATA", PodCastData::class.java)!!
        } else {
            intent.getParcelableArrayListExtra<PodCastData>("PODCAST_DATA")!!
        }
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        contentDashboardDataBinding = dataBinding.contentContainer
        contentAppBarDataBinding = dataBinding.appBarContainer
        /*Back Button set to GONE for home page*/
        contentAppBarDataBinding.imgBackButton.visibility = View.GONE
        contentAppBarDataBinding.appBarTitleText.text = getString(R.string.home)


        contentDashboardDataBinding.recyclerView.apply {
            mAdapter = PodCastAdapter(this@DashboardActivity,podcastList)
            adapter = mAdapter
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemSelected(podcastData: PodCastData, position: Int) {
        podcastItem = podcastData
        mSelectedPosition = position
        val mIntent = Intent(this, PodCastDetailActivity::class.java)
        mIntent.putExtra("DETAIL_VIEW", podcastItem)
        openPodCastDetailViewLauncher.launch(mIntent)
    }
}