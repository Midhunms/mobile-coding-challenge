package audiobooks.codingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.audiobooks.codingchallenge.R
import com.audiobooks.codingchallenge.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var dataBinding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }
}