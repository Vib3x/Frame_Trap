package com.frametrap.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar!!.title = "About"


        /*try {
            val pInfo: PackageInfo =
                content.getPackageManager().getPackageInfo(context.getPackageName(), 0)
            val version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }*/
    }
}