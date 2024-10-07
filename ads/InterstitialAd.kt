package com.drum.pad.music.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import com.base.core.utils.SharePrefUtils
import com.drum.pad.music.MyApplication
import com.drum.pad.music.helper.RemoteConfig
import com.drum.pad.music.util.AppFlyer
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.Locale

class InterAdsManager(private val context: Context) {

    private var adIsLoading = false

    fun loadAd() {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowInters()) {
            return
        }

        if (adIsLoading || minterstitialAd != null) {
            return
        }

        adIsLoading = true
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            KeyAds.keyInter,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    minterstitialAd = interstitialAd
                    adIsLoading = false
                    AppFlyer.logEventLoadInterAds(context)

                    minterstitialAd?.setOnPaidEventListener { adValue ->
                        AppFlyer.logAdsRevenue(
                            adValue,
                            minterstitialAd?.adUnitId.toString(),
                            "InterstitialAd",
                            minterstitialAd?.responseInfo?.mediationAdapterClassName
                        )
                    }

                    Log.i(TAG, "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.message)
                    minterstitialAd = null
                    adIsLoading = false
                    val error = String.format(
                        Locale.US,
                        "domain: %s, code: %d, message: %s",
                        loadAdError.domain,
                        loadAdError.code,
                        loadAdError.message
                    )
                    Log.d(
                        TAG, "onAdFailedToLoad: with error: $error"
                    )
                }
            })
    }

    private fun reloadAd() {
        adIsLoading = false
        minterstitialAd = null
        loadAd()
    }

    fun showInterstitial(activity: Activity?, function: () -> Unit) {

        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowInters()) {
            function.invoke()
            return
        }

        Log.d(TAG, "showInterstitial: ${minterstitialAd != null}")
        // Show the ad if it's ready.
        // Otherwise restart the game.
        if (minterstitialAd != null) {
            val capping = SharePrefUtils.getLong("interCapping")

            if (System.currentTimeMillis() - capping < (RemoteConfig.getInterCapping() * 1000)) {
                function.invoke()
                return
            }
            minterstitialAd!!.show(activity!!)
            minterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    ///action in ads
                    Log.d(TAG, "onAdShowedFullScreenContent: show")
                    AppFlyer.logEventShowInterAds(context)
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    ///action affter ads
                    SharePrefUtils.saveKey("interCapping", System.currentTimeMillis())
                    function.invoke()
                    reloadAd()
                }

                @SuppressLint("DefaultLocale")
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    val error = String.format(
                        "domain: %s, code: %d, message: %s",
                        adError.domain,
                        adError.code,
                        adError.message
                    )
                    Log.e(
                        TAG, "onAdFailedToShowFullScreenContent: $error"
                    )
                }


                override fun onAdClicked() {
                    super.onAdClicked()
                    AppFlyer.logEventClickInterAds(context)

                }
            }
        } else {
            function.invoke()
            reloadAd()
        }
    }


    companion object {
        private const val TAG = "INTER ADS "
        var minterstitialAd: InterstitialAd? = null
    }
}
