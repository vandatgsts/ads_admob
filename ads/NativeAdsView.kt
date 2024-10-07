package com.drum.pad.music.ads

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.drum.pad.music.MyApplication
import com.drum.pad.music.databinding.IntroNativeAdsBinding
import com.drum.pad.music.databinding.ItemSmallListAdsBinding
import com.drum.pad.music.databinding.MediumNativeAdsBinding
import com.drum.pad.music.databinding.SmallNativeAdsBinding
import com.drum.pad.music.helper.RemoteConfig
import com.drum.pad.music.util.AppFlyer
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdsView(
    private val context: Context,
    nativeAds: FrameLayout,
    private val layoutInflater: LayoutInflater,
) {
    companion object {
        private const val TAG = "NativeAdsView"
    }

    private var mNativeAd: NativeAd? = null
    private var nativeAdsView: FrameLayout = nativeAds

    fun loadAds() {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowNative()) {
            return
        }

        val adBuilder = AdLoader.Builder(context, KeyAds.keyNativeAds)
        adBuilder.forNativeAd { nativeAd ->
            mNativeAd?.destroy()
            mNativeAd = nativeAd
            val unifiedAdBinding: SmallNativeAdsBinding =
                SmallNativeAdsBinding.inflate(layoutInflater)

            NativeAdsManager.populateNativeAdViewSmall(nativeAd, unifiedAdBinding)
            nativeAdsView.removeAllViews()
            nativeAdsView.addView(unifiedAdBinding.root)

        }
        val adLoader = adBuilder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
                Log.e(TAG, "onAdFailedToLoad: $error")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()

                mNativeAd?.setOnPaidEventListener { adValue ->
                    AppFlyer.logAdsRevenue(
                        adValue,
                        KeyAds.keyNativeAds,
                        "Native Ads",
                        mNativeAd?.responseInfo?.mediationAdapterClassName
                    )
                }
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun loadAdsItemListView(idKeyAds: Int) {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowNative()) {
            return
        }

        val keyAds = KeyAds.keyNativeAds

        val adBuilder = AdLoader.Builder(context, keyAds)
        adBuilder.forNativeAd { nativeAd ->
            mNativeAd?.destroy()
            mNativeAd = nativeAd

            val unifiedAdBinding: ItemSmallListAdsBinding =
                ItemSmallListAdsBinding.inflate(layoutInflater)
            NativeAdsManager.populateNativeAdViewSmallItem(nativeAd, unifiedAdBinding)

            nativeAdsView.removeAllViews()
            nativeAdsView.addView(unifiedAdBinding.root)
        }
        val adLoader: AdLoader = adBuilder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
                Log.e(TAG, "onAdFailedToLoad: $error")
//                if (idKeyAds < 3) {
//                    loadAdsItemListView(idKeyAds + 1)
//                }

            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mNativeAd?.setOnPaidEventListener { adValue ->
                    AppFlyer.logAdsRevenue(
                        adValue,
                        keyAds,
                        "Native Ads",
                        mNativeAd?.responseInfo?.mediationAdapterClassName
                    )
                }
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }


    fun onDestroy() {
        mNativeAd?.destroy()
    }

    fun loadMediumAds(idKeyAds: Int) {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowNative()) {
            return
        }

        val keyAds =    KeyAds.keyNativeAds
        val adBuilder = AdLoader.Builder(context, keyAds)
        adBuilder.forNativeAd { nativeAd ->
            mNativeAd?.destroy()
            mNativeAd = nativeAd
            val unifiedAdBinding: MediumNativeAdsBinding =
                MediumNativeAdsBinding.inflate(layoutInflater)
            NativeAdsManager.populateNativeAdMediumView(nativeAd, unifiedAdBinding)
            nativeAdsView.removeAllViews()
            nativeAdsView.addView(unifiedAdBinding.root)
        }
        val adLoader = adBuilder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
                Log.e(TAG, "onAdFailedToLoad: $error")
//                if (idKeyAds < 3) {
//                    loadMediumAds(idKeyAds + 1)
//                }

            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mNativeAd?.setOnPaidEventListener { adValue ->
                    AppFlyer.logAdsRevenue(
                        adValue,
                        KeyAds.keyNativeAds,
                        "Native Ads",
                        mNativeAd?.responseInfo?.mediationAdapterClassName
                    )
                }
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun loadIntroAds() {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowNative()) {
            return
        }

        val adBuilder = AdLoader.Builder(context, KeyAds.keyNativeAds)
        adBuilder.forNativeAd { nativeAd ->
            mNativeAd?.destroy()
            mNativeAd = nativeAd
            val unifiedAdBinding: IntroNativeAdsBinding =
                IntroNativeAdsBinding.inflate(layoutInflater)
            NativeAdsManager.populateNativeIntroAdViewSmall(nativeAd, unifiedAdBinding)
            nativeAdsView.removeAllViews()
            nativeAdsView.addView(unifiedAdBinding.root)
        }
        val adLoader = adBuilder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
                Log.e(TAG, "onAdFailedToLoad: $error")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mNativeAd?.setOnPaidEventListener { adValue ->
                    AppFlyer.logAdsRevenue(
                        adValue,
                        KeyAds.keyNativeAds,
                        "Native Ads",
                        mNativeAd?.responseInfo?.mediationAdapterClassName
                    )
                }
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }
}