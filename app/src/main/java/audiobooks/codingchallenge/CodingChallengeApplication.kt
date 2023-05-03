package audiobooks.codingchallenge

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CodingChallengeApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: CodingChallengeApplication? = null

        fun applicationContext() : Context? {
            return instance?.applicationContext
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()

    }

}