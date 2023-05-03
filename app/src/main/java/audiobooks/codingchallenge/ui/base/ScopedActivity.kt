package audiobooks.codingchallenge.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class ScopedActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        job = Job()
    }

    override fun setContentView(layoutResID: Int) {

        super.setContentView(layoutResID)
        bindUI()
    }



    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    protected open fun bindUI(): Job = launch { }

}