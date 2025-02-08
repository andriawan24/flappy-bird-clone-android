package id.andriawan24.flappybirdclone

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        @JvmStatic
        private var instance: MainApplication? = null

        @JvmStatic
        fun getContext(): Context? {
            return instance
        }
    }
}
