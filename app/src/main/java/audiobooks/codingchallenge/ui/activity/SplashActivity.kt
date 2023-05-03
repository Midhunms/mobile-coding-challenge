package audiobooks.codingchallenge.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import audiobooks.codingchallenge.ui.base.ScopedActivity
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import audiobooks.codingchallenge.data.dto.PodCastData
import audiobooks.codingchallenge.data.dto.ResponseData
import audiobooks.codingchallenge.ui.base.UIState
import java.util.ArrayList

@AndroidEntryPoint
class SplashActivity : ScopedActivity() {
    private lateinit var dataBinding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        viewModel.processBestPodCastList()

       /* CoroutineScope(Dispatchers.IO).launch {

            delay(4000)
            startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
        }*/
    }

    override fun bindUI() = launch(Dispatchers.Main) {
        viewModel.getBestPodCastResponse.state.observe(this@SplashActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress

                    dataBinding.pbLoading.visibility = View.VISIBLE
                    dataBinding.errorText.visibility = View.GONE
                }
                is UIState.Retrying -> { // Show Retry Progress
                    dataBinding.pbLoading.visibility = View.VISIBLE
                    dataBinding.errorText.visibility = View.GONE
                }
                is UIState.Failure -> { // On Failure
                    dataBinding.pbLoading.visibility = View.GONE
                    dataBinding.errorText.visibility = View.VISIBLE
                    dataBinding.errorText.text = getString(R.string.error_message)
                }
                is UIState.Success -> { // On Success
                    dataBinding.pbLoading.visibility = View.GONE
                    dataBinding.errorText.visibility = View.GONE
                    val mData = state.data as ResponseData
                    startDashboardActivity(mData.podcasts)
                    Log.e("","")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }
        }
    }

    private fun startDashboardActivity(podcasts: ArrayList<PodCastData>) {
        val intent = Intent(this,DashboardActivity::class.java)
        intent.putParcelableArrayListExtra("PODCAST_DATA",podcasts)
        startActivity(intent)
    }
}