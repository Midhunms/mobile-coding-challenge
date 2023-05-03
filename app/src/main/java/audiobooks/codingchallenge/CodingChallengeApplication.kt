package audiobooks.codingchallenge

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CodingChallengeApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: CodingChallengeApplication? = null

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
        /*Fresco is used for loading network image*/
        Fresco.initialize(this)
    }

}