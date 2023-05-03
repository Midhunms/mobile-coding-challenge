package audiobooks.codingchallenge.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import audiobooks.codingchallenge.ui.base.ScopedActivity
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import audiobooks.codingchallenge.data.dto.ResponseData
import audiobooks.codingchallenge.ui.base.UIState

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

                }
                is UIState.Retrying -> { // Show Retry Progress

                }
                is UIState.Failure -> { // On Failure

                }
                is UIState.Success -> { // On Success
                    val mData = state.data as ResponseData
                    Log.e("","")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }
        }
    }
}