package com.frametrap.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar!!.title = "About"
        findViewById<TextView>(R.id.about_version).text = "Version " + BuildConfig.VERSION_NAME


        /*try {
            val pInfo: PackageInfo =
                content.getPackageManager().getPackageInfo(context.getPackageName(), 0)
            val version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }*/
    }
}