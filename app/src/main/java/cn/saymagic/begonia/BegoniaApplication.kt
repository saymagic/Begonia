package cn.saymagic.begonia

import android.app.Application
import cn.saymagic.begonia.sdk.core.Unsplash

class BegoniaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashHandler.getInstance().init()
        Unsplash.init(BuildConfig.APPLICATION_KEY)
    }
    
}