package com.headapp.darkmode;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.headapp.darkmode.R;


public class AdManager {

    private static AdManager singleton;
    private static InterstitialAd interstitialAd;

    public AdManager() {
    }

    /***
     * returns an instance of this class. if singleton is null create an instance
     * else return  the current instance
     * @return
     */
    public static AdManager getInstance() {
        if (singleton == null) {
            singleton = new AdManager();
        }

        return singleton;
    }

    /***
     * Create an interstitial ad
     * @param context
     */
    public static void createAd(Context context) {

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.instellar_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /***
     * get an interstitial Ad
     * @return
     */

    public static InterstitialAd getAd() {
        return interstitialAd;
    }


    public void createAgain(){

        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
}