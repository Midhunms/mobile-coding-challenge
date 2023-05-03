package audiobooks.codingchallenge.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import audiobooks.codingchallenge.ui.base.ScopedActivity
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivityDashboardBinding
import com.audiobooks.codingchallenge.databinding.ContentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ScopedActivity() {

    private lateinit var dataBinding: ActivityDashboardBinding
    private lateinit var contentDashboardDataBinding: ContentDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        contentDashboardDataBinding = dataBinding.contentContainer
    }
}