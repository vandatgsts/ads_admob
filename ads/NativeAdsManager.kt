

import android.view.View

import com.google.android.gms.ads.nativead.NativeAd

object NativeAdsManager {
    fun populateNativeAdViewSmall(
        nativeAd: NativeAd, unifiedAdBinding: SmallNativeAdsBinding
    ) {
        val nativeAdView = unifiedAdBinding.root

        nativeAdView.adChoicesView = unifiedAdBinding.adAdchoices

        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
//            nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
//            nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//            nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//            if (nativeAd.body == null) {
//                unifiedAdBinding.adBody.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adBody.visibility = View.VISIBLE
//                unifiedAdBinding.adBody.text = nativeAd.body
//            }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)
    }

    fun populateNativeAdViewSmallItem(
        nativeAd: NativeAd, unifiedAdBinding: ItemSmallListAdsBinding
    ) {
        val nativeAdView = unifiedAdBinding.root
        val adChoicesView = unifiedAdBinding.adAdchoices // Thêm dòng này
        nativeAdView.adChoicesView = adChoicesView
        // Set the media view.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
//            nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
//            nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//            nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//            if (nativeAd.body == null) {
//                unifiedAdBinding.adBody.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adBody.visibility = View.VISIBLE
//                unifiedAdBinding.adBody.text = nativeAd.body
//            }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        unifiedAdBinding.loadingAds.visibility = View.GONE
        nativeAdView.setNativeAd(nativeAd)
    }

    fun populateNativeAdViewSmallItemMyMusic(
        nativeAd: NativeAd, unifiedAdBinding: ItemSmallListAdsMyMusicBinding
    ) {
        val nativeAdView = unifiedAdBinding.root
        val adChoicesView = unifiedAdBinding.adAdchoices // Thêm dòng này
        nativeAdView.adChoicesView = adChoicesView
        // Set the media view.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
//            nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
//            nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//            nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//            if (nativeAd.body == null) {
//                unifiedAdBinding.adBody.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adBody.visibility = View.VISIBLE
//                unifiedAdBinding.adBody.text = nativeAd.body
//            }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        unifiedAdBinding.loadingAds.visibility = View.GONE
        nativeAdView.setNativeAd(nativeAd)
    }


    fun populateNativeAdViewSmallSquareAds(
        nativeAd: NativeAd, unifiedAdBinding: ItemSquareGridAdsBinding
    ) {
        val nativeAdView = unifiedAdBinding.root
        val adChoicesView = unifiedAdBinding.adAdchoices // Thêm dòng này
        nativeAdView.adChoicesView = adChoicesView
        // Set the media view.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
//            nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
//            nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.mediaContent = it }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//            if (nativeAd.body == null) {
//                unifiedAdBinding.adBody.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adBody.visibility = View.VISIBLE
//                unifiedAdBinding.adBody.text = nativeAd.body
//            }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }
        if (nativeAd.images.isEmpty()) {
            unifiedAdBinding.adImageView.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adImageView.mediaContent = nativeAd.mediaContent
            unifiedAdBinding.adImageView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        unifiedAdBinding.loadingAds.visibility = View.GONE
        nativeAdView.setNativeAd(nativeAd)
    }

    fun populateNativeAdViewSmallSquareAdsHome(
        nativeAd: NativeAd, unifiedAdBinding: ItemSquareGridAdsHomeBinding
    ) {
        val nativeAdView = unifiedAdBinding.root
        val adChoicesView = unifiedAdBinding.adAdchoices // Thêm dòng này
        nativeAdView.adChoicesView = adChoicesView
        // Set the media view.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
//            nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
//            nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.mediaContent = it }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//            if (nativeAd.body == null) {
//                unifiedAdBinding.adBody.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adBody.visibility = View.VISIBLE
//                unifiedAdBinding.adBody.text = nativeAd.body
//            }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }
        if (nativeAd.images.isEmpty()) {
            unifiedAdBinding.adImageView.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adImageView.setImageDrawable(nativeAd.images[0].drawable)
            unifiedAdBinding.adImageView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        unifiedAdBinding.loadingAds.visibility = View.GONE
        nativeAdView.setNativeAd(nativeAd)
    }

    fun populateNativeAdMediumView(
        nativeAd: NativeAd, unifiedAdBinding: MediumNativeAdsBinding
    ) {
        val nativeAdView = unifiedAdBinding.root

        // Set the media view.
        nativeAdView.mediaView = unifiedAdBinding.adMedia

        // Set other ad assets.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.mediaContent = it }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.VISIBLE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.

        nativeAdView.setNativeAd(nativeAd)
    }

    fun populateNativeIntroAdViewSmall(
        nativeAd: NativeAd, unifiedAdBinding: IntroNativeAdsBinding
    ) {
        val nativeAdView = unifiedAdBinding.root
        val adChoicesView = unifiedAdBinding.adAdchoices // Thêm dòng này
        nativeAdView.adChoicesView = adChoicesView
        // Set the media view.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline

        // Set other ad assets.
//            nativeAdView.headlineView = unifiedAdBinding.headline
//            nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon

        nativeAdView.imageView = unifiedAdBinding.adImageView

//            nativeAdView.priceView = unifiedAdBinding.adPrice
//        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser
        nativeAdView.bodyView = unifiedAdBinding.adBody

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
//            nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.VISIBLE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.images.isEmpty()) {
            unifiedAdBinding.adImageView.visibility = View.GONE
        } else {
            unifiedAdBinding.adImageView.setImageDrawable(nativeAd.images[0].drawable)
            unifiedAdBinding.adImageView.visibility = View.VISIBLE
        }

//            if (nativeAd.price == null) {
//                unifiedAdBinding.adPrice.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adPrice.visibility = View.VISIBLE
//                unifiedAdBinding.adPrice.text = nativeAd.price
//            }
//
//            if (nativeAd.store == null) {
//                unifiedAdBinding.adStore.visibility = View.INVISIBLE
//            } else {
//                unifiedAdBinding.adStore.visibility = View.VISIBLE
//                unifiedAdBinding.adStore.text = nativeAd.store
//            }

//        if (nativeAd.advertiser == null) {
//            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
//        } else {
//            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
//            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
//        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)
    }
}