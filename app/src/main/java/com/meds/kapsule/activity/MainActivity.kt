package com.meds.kapsule.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.meds.kapsule.R
import com.meds.kapsule.carousel.ViewPagerCarouselView

class MainActivity : AppCompatActivity() {

    private var viewPagerCarouselView: ViewPagerCarouselView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carouselSlideInterval: Long = 5000 // 5 SECONDS
        val imageResourceIds = intArrayOf(
                R.drawable.car1, R.drawable.car2,
                R.drawable.car1, R.drawable.car2,
                R.drawable.car1, R.drawable.car2
        )

        viewPagerCarouselView = findViewById<View>(R.id.carousel_view) as ViewPagerCarouselView?
        viewPagerCarouselView?.setData(supportFragmentManager, imageResourceIds, carouselSlideInterval)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewPagerCarouselView?.onDestroy()
    }
}
