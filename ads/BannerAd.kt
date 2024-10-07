package com.drum.pad.music.ads

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import com.drum.pad.music.MyApplication
import com.drum.pad.music.helper.RemoteConfig
import com.drum.pad.music.util.AppFlyer

import com.google.ads.mediation.admob.AdMobAdapter

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdBanner(
    var context: Context, var view: ViewGroup
) {
    private var adView: AdView? = null

    fun loadBanner(isCollapsible: Boolean = true) {
        // Create a new ad view.
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowCollapse()) {
            return
        }

        adView = AdView(context).apply {
            adUnitId = KeyAds.keyBannerADs
        }
        adView?.setAdSize(adSize)

        // Replace ad container with new ad view.
        view.removeAllViews()
        view.addView(adView)

        // Start loading the ad in the background.

        if (isCollapsible) {
            val adRequest =
                AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter::class.java, Bundle().apply {
                        putString("collapsible", "bottom")
                    }).build()
            adView?.loadAd(adRequest)
        } else {
            val adRequest =
                AdRequest.Builder()
                    .addNetworkExtrasBundle(
                        AdMobAdapter::class.java, Bundle()
                    ).build()
            adView?.loadAd(adRequest)
        }



        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.i("AdsInformation", "admob banner onAdLoaded")
                adView?.setOnPaidEventListener { adValue ->
                    adView?.adUnitId?.let {
                        AppFlyer.logAdsRevenue(
                            adValue,
                            it,
                            "Banner",
                            adView?.responseInfo?.mediationAdapterClassName
                        )
                    }
                }
                super.onAdLoaded()

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.e("AdsInformation", "admob banner onAdFailedToLoad: ${adError.message}")
                super.onAdFailedToLoad(adError)
            }

            override fun onAdImpression() {
                Log.d("AdsInformation", "admob banner onAdImpression")
                super.onAdImpression()
            }

            override fun onAdClicked() {
                Log.d("AdsInformation", "admob banner onAdClicked")

                super.onAdClicked()
            }

            override fun onAdClosed() {
                Log.d("AdsInformation", "admob banner onAdClosed")

                super.onAdClosed()
            }

            override fun onAdOpened() {
                Log.d("AdsInformation", "admob banner onAdOpened")

                super.onAdOpened()
            }
        }
    }

    private val adSize: AdSize
        get() {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            val density = outMetrics.density
            var adWidthPixels = view.width.toFloat()


            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
        }

    fun dismiss() {
        adView?.destroy()
    }
}