

import android.content.Context
import android.util.Log
import com.drum.pad.music.util.AppFlyer
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardAdsManager(private val context: Context) {

    companion object {
        private var rewardedAd: RewardedAd? = null
        private var hasEarnedReward: Boolean = false
        private var isAdDismissed: Boolean = false
        private lateinit var pendingRewardAction: () -> Unit
        private lateinit var rewardItem: RewardItem
    }

    init {
        loadRewardedAd()
    }

    fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(context, KeyAds.keyRewardAds, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Handle the error
                rewardedAd = null
                Log.d("RewardAdsManager", "Ad failed to load: ${adError.message}")
            }

            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
                Log.d("RewardAdsManager", "Ad was loaded.")
                AppFlyer.logEventRewardAds(context)
                rewardedAd?.setOnPaidEventListener { adValue ->
                    AppFlyer.logAdsRevenue(
                        adValue,
                        rewardedAd?.adUnitId.toString(),
                        "RewardAds",
                        rewardedAd?.responseInfo?.mediationAdapterClassName
                    )
                }
                setFullScreenContentCallback()
            }
        })
    }

    private fun setFullScreenContentCallback() {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                Log.d("RewardAdsManager", "Ad was shown.")
                hasEarnedReward = false // Reset the reward flag
                isAdDismissed = false // Reset the dismissed flag
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d("RewardAdsManager", "Ad was dismissed.")
                isAdDismissed = true
                checkAndExecutePostRewardFunction()
                rewardedAd = null
                loadRewardedAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.d("RewardAdsManager", "Ad failed to show: ${adError.message}")
                rewardedAd = null
            }
        }
    }

    fun showRewardedAd(onUserEarnedReward: () -> Unit) {
        rewardedAd?.let { ad ->
            pendingRewardAction = onUserEarnedReward
            ad.show(context as android.app.Activity) { rewardItem ->
                Log.d(
                    "RewardAdsManager",
                    "User earned the reward: ${rewardItem.type}, amount: ${rewardItem.amount}"
                )
                hasEarnedReward = true
            }
        } ?: run {
            Log.d("RewardAdsManager", "The rewarded ad wasn't ready yet.")
        }
    }

    private fun checkAndExecutePostRewardFunction() {
        // Execute only if the reward is earned and the ad is dismissed
        if (hasEarnedReward && isAdDismissed) {
            pendingRewardAction() // Execute the pending action with the reward item
        }
    }
}
