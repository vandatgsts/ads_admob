

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

private const val TAG = "loadNativeAds"

object LoadAdsAdapter {
    fun loadNativeAds(
        idKeyAds: Int,
        context: Context,
        loadSuccess: (nativeAd: NativeAd?) -> Unit,
        loadFailed: (() -> Unit)? = null
    ) {

        val keyAds =  KeyAds.keyNativeAds
        var nativeAd: NativeAd? = null
        val adLoader: AdLoader?

        val builder = AdLoader.Builder(context, keyAds)
        adLoader = builder.forNativeAd { nativeAds ->
            nativeAd = nativeAds
            nativeAd?.setOnPaidEventListener { adValue ->
                AppFlyer.logAdsRevenue(
                    adValue,
                    keyAds,
                    "Native Ads",
                    nativeAd?.responseInfo?.mediationAdapterClassName
                )
            }
        }.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG, "Native-loadAds1 success: $keyAds")
                loadSuccess.invoke(nativeAd)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.e(TAG, "load native ad1 failed $loadAdError")
                loadFailed?.invoke()
//                if (idKeyAds < 3) {
//                    loadNativeAds(idKeyAds + 1, context, loadSuccess, loadFailed)
//                }

            }

            override fun onAdClicked() {
                super.onAdClicked()
            }
        }).build()
        val adRequest = AdRequest.Builder().build()
        adLoader.loadAd(adRequest)
    }
}