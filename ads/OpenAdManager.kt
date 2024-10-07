

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date

class OpenAdManager(
    private val activity: Activity
) {
    private var appOpenAd: AppOpenAd? = null
    private var loadTime: Long = 0

    companion object {
        private const val LOG_TAG = "AppOpenAd"
        private val AD_UNIT_ID = KeyAds.keyOpenAds
        private var isShowingAd = false
    }

    val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    fun showAdIfAvailable(onDismiss: (() -> Unit)? = null) {
        if (MyApplication.isPremium.value == true || !RemoteConfig.getShowOpen()) {
            onDismiss?.invoke()
            return
        }

        if (!isShowingAd && isAdAvailable) {
            Log.d(LOG_TAG, "Will show ad.")
            val fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isShowingAd = false
                    onDismiss?.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: $adError")
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                }
            }
            appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd?.show(activity)
        } else {
            Log.d(LOG_TAG, "Cannot show ad.")
            onDismiss?.invoke()
        }
    }

    fun fetchAd() {
        if (isAdAvailable) {
            return
        }

        val loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
                Log.d(LOG_TAG, "load ad success")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.d(LOG_TAG, "failed to load")
            }
        }
        val request = adRequest
        AppOpenAd.load(activity, AD_UNIT_ID, request, loadCallback)
    }
}