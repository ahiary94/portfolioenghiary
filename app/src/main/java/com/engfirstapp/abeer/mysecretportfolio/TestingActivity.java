package com.engfirstapp.abeer.mysecretportfolio;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

public class TestingActivity extends AppCompatActivity {

    private AdView adView;
    private AdRequest adRequest;
    //ca-app-pub-3940256099942544/2247696110
    //ca-app-pub-7003859395474560/1989163815
    private static String LOG_TAG = "EXAMPLE";
    NativeExpressAdView mAdView;
    VideoController mVideoController;
    AdLoader.Builder adLoader;
    AdLoader loader;
//    UnifiedNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

//        String android_id = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        adView = findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    void loadOne() {
        //300x250

// Set its video options.
        mAdView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

// The VideoController can be used to get lifecycle events and info about an ad's video
// asset. One will always be returned by getVideoController, even if the ad has no video
// asset.
        mVideoController = mAdView.getVideoController();
        mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
                Log.d(LOG_TAG, "Video playback is finished.");
                super.onVideoEnd();
            }
        });

// Set an AdListener for the AdView, so the Activity can take action when an ad has finished
// loading.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mVideoController.hasVideoContent()) {
                    Log.d(LOG_TAG, "Received an ad that contains a video asset.");
                } else {
                    Log.d(LOG_TAG, "Received an ad that does not contain a video asset.");
                }
            }
        });

        mAdView.loadAd(new AdRequest.Builder().build());

    }

    void loadTwo() {
//"ca-app-pub-3940256099942544/2247696110")
        //ca-app-pub-7003859395474560/1989163815
//        MobileAds.initialize(this, "ca-app-pub-7003859395474560~7094150837");

//        adLoader = new AdLoader.Builder(this, "ca-app-pub-7003859395474560/8988459074");
                loader = adLoader.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        //request unified native ads. When an ad has loaded successfully,
                        // the listener object's onUnifiedNativeAdLoaded() method is called.

                        if (loader.isLoading()) {
                            Toast.makeText(TestingActivity.this, "loading", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TestingActivity.this, "not loading", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
//        loader.loadAd(new AdRequest.Builder().build());
        loader.loadAd(new AdRequest.Builder().build());


    }

}

//public class MyActivity extends Activity {
//    private NativeExpressAdView mNativeExpressAdView;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        // Create a native express ad. The ad size and ad unit ID must be set before calling
//        // loadAd.
//        mNativeExpressAdView = new NativeExpressAdView(this);
//        mNativeExpressAdView.setAdSize(new AdSize(400, 100));
//        mNativeExpressAdView.setAdUnitId("myAdUnitId");
//
//        // Create an ad request.
//        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
//
//        // Add the NativeExpressAdView to the view hierarchy.
//        layout.addView(mNativeExpressAdView);
//
//        // Start loading the ad.
//        mNativeExpressAdView.loadAd(adRequestBuilder.build());
//
//        setContentView(layout);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // Resume the NativeExpressAdView.
//        mNativeExpressAdView.resume();
//    }
//
//    @Override
//    public void onPause() {
//        // Pause the NativeExpressAdView.
//        mNativeExpressAdView.pause();
//
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        // Destroy the NativeExpressAdView.
//        mNativeExpressAdView.destroy();
//
//        super.onDestroy();
//    }
//}