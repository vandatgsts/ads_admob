

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date
import java.util.Locale

class AppOpenAdManager(
    private val application: Application
) : DefaultLifecycleObserver, ActivityLifecycleCallbacks {

    private var appOpenAd: AppOpenAd? = null
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0

    init {
        this.application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        fetchAd()
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    private fun showAdIfAvailable(onDismiss: (() -> Unit)? = null) {
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
                    fetchAd()
                    onDismiss?.invoke()
                    currentActivity?.let {
                        val intent = Intent(currentActivity, SubActivity::class.java)
                        currentActivity?.startActivity(intent)
                    }

                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    val error = String.format(
                        Locale.US,
                        "domain: %s, code: %d, message: %s",
                        adError.domain,
                        adError.code,
                        adError.message
                    )

                    Log.e(LOG_TAG, error)
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                }
            }
            appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
            currentActivity?.let {
                appOpenAd?.show(it)
            }
        } else {
            Log.d(LOG_TAG, "Cannot show ad.")
            fetchAd()
            onDismiss?.invoke()
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        if (!RemoteConfig.getShowOpen()) {
            return
        }

        if (MyApplication.isPremium.value == false && MyApplication.isShowOpen) {
            showAdIfAvailable()
        } else {
            MyApplication.isShowOpen = true
        }

        Log.d(LOG_TAG, "onStart")
    }

    fun fetchAd() {
        if (isAdAvailable) {
            return
        }

        val loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
                appOpenAd?.setOnPaidEventListener { adValue ->
                    appOpenAd?.adUnitId?.let {
                        AppFlyer.logAdsRevenue(
                            adValue,
                            it,
                            "OpenAds",
                            appOpenAd?.responseInfo?.mediationAdapterClassName
                        )
                    }
                }
                Log.d(LOG_TAG, "load ad success")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.d(LOG_TAG, "failed to load")
            }
        }

        currentActivity?.let {
            AppOpenAd.load(it, AD_UNIT_ID, adRequest, loadCallback)
        }
    }

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }

    companion object {
        private const val LOG_TAG = "AppOpenAdManager"
        private val AD_UNIT_ID = KeyAds.keyOpenAds
        private var isShowingAd = false
    }
}
